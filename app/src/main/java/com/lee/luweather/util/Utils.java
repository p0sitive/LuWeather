package com.lee.luweather.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lee.luweather.R;
import com.lee.luweather.base.Constant;
import com.lee.luweather.model.City;
import com.lee.luweather.model.CityWeather;
import com.lee.luweather.model.LocalCity;
import com.lee.luweather.model.Weather;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.db.Selector;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;


/**
 * 工具类
 * Created by Administrator on 2016-1-21.
 */
public class Utils {

    static Handler handler;

    static Utils instance;

    static Context mContext;

    public static String local = "test";

    static {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Intent intent = null;
                switch (msg.what) {
                    case 0x11:
                        local = (String) msg.obj;
                        getCity();
                        break;
                    case 0x12:
                        LocalCity city = (LocalCity) msg.obj;
                        /*intent = new Intent(Constant.INTENT_ACTION_GETCITY);
                        bundle = new Bundle();
                        bundle.putInt("type",0);
                        bundle.putString("city", city.getResult().getAddressComponent().getDistrict());
                        intent.putExtras(bundle);
                        */
                        String cityName = city.getResult().getAddressComponent().getDistrict();
                        getWeatherByCityName(cityName);
                        break;
                    case 0x13:
                        intent = new Intent(Constant.INTENT_ACTION_ERROR);
                        break;
                    case 0x14:
                        intent = new Intent(Constant.INTENT_ACTION_GPSFAIL);
                        showToast(mContext, "定位失败");
                        break;
                    case 0x15:
                        intent = new Intent(Constant.INTENT_ACTION_GETWEATHER);
                        Bundle bundle = msg.getData();
                        bundle.putString("weather", (String) msg.obj);
                        intent.putExtras(bundle);
                }
                if (intent != null) {
                    mContext.sendBroadcast(intent);
                }
            }
        };
    }

    public static Utils getIntence(Context context) {
        if (instance == null) {
            instance = new Utils();
        }
        mContext = context;
        return instance;
    }

    /**
     * 通过城市名称获取天气信息，（默认北京）
     *
     * @param cityName
     */
    public static void getWeatherByCityName(String cityName) {
        DbManager db = getDbHelper();
        cityName = cityName.replace("区", "");
        cityName = cityName.replace("县", "");

        //从数据库中获取，避免连续获取
        try {
            CityWeather weather = db.selector(CityWeather.class).where("cityname", "=", cityName).orderBy("id", true).findFirst();
            if (weather != null && DateUtil.countDatetime(weather.getDate()) < 5 * 60 * 1000) {
                Message msg = handler.obtainMessage();
                msg.obj = weather.getWeather();
                msg.what = 0x15;
                Bundle bundle = new Bundle();
                bundle.putString("city", cityName);
                msg.setData(bundle);
                Log.i("xxx_db", weather.getWeather());
                handler.sendMessage(msg);
                return;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


        City city = null;
        try {
            city = db.selector(City.class).where("city", "=", cityName).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        String cityid = "101010100";//city.getNumber();
        if (city != null) {
            cityid = city.getNumber();
        }
        getWeather(cityid);
    }

    public static DbManager getDbHelper() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().
                setDbDir(new File(Constant.getDbPath())).
                setDbName("city.db");
        return x.getDb(daoConfig);
    }

    /**
     * 获取天气信息，通过广播传递信息
     *
     * @param cityid
     */
    public static void getWeather(final String cityid) {
        try {
            final City city = getDbHelper().selector(City.class).where("number", "=", cityid).findFirst();
            String url = Constant.WEATHER_URL.replace(Constant.CITYID_STR, cityid);
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    Message msg = handler.obtainMessage();
                    msg.obj = result;
                    msg.what = 0x15;
                    Bundle bundle = new Bundle();
                    bundle.putString("city", city.getCity());
                    msg.setData(bundle);
                    Log.i("xxx_http", result);
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void getLocal(LocationManager lm) {
        if (lm != null) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1000, new MyLocation());
        }
    }

    private static void getCity() {
        RequestParams params = new RequestParams(Constant.LOCATION_URL.replace(Constant.POINT, local));
        params.addParameter("coordtype", "wgs84ll");
        params.addParameter("location", local);
        params.setCacheMaxAge(1000 * 60);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("xxx", result);
                LocalCity city = new Gson().fromJson(result, LocalCity.class);

                Message msg = handler.obtainMessage();
                msg.obj = city;
                msg.what = 0x12;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message msg = handler.obtainMessage();
                msg.obj = ex;
                msg.what = 0x13;
                handler.sendMessage(msg);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    class MyLocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                local = location.getLatitude() + "," + location.getLongitude();
                Message msg = handler.obtainMessage();
                msg.what = 0x11;
                msg.obj = local;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            //handler.sendEmptyMessage(0x14);

        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showSeakBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * 根据天气名称获取天气图标资源id
     *
     * @param weather_type 天气名称
     * @param size         类别：a：大图，b：中图
     * @return
     */
    public static int getWeatherTypeRsId(String weather_type, String size) {
        int rsId = 0;
        String[] arr = mContext.getResources().getStringArray(R.array.weather_type);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(weather_type)) {
                rsId = i;
                break;
            }
        }
        rsId = getRsId(size + "_" + rsId, "drawable");
        return rsId;
    }

    private static int getRsId(String rsName, String rsType) {
        Resources res = mContext.getResources();
        int resId = res.getIdentifier(rsName, rsType, mContext.getPackageName());
        return resId;
    }

    public static class WeatherHolder {
        public String city;
        public Weather weather;

        public WeatherHolder() {
        }

        public WeatherHolder(String city, Weather weather) {
            this.weather = weather;
            this.city = city;
        }
    }

    public String getSeparateString(TextView textView, String string) {
        String str = string;
        return str;
    }
}
