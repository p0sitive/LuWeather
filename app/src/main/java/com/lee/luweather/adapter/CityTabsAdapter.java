package com.lee.luweather.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lee.luweather.R;
import com.lee.luweather.model.Weather;
import com.lee.luweather.util.DateUtil;
import com.lee.luweather.util.Utils;
import com.lee.luweather.view.IconView;
import com.lee.luweather.view.SlidingTabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
 * The individual pages are simple and just display two lines of text. The important section of
 * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
 * {@link SlidingTabLayout}.
 */
public class CityTabsAdapter extends PagerAdapter {

    SparseArray<View> views = new SparseArray<View>();

    public List<Utils.WeatherHolder> getList() {
        return list;
    }

    public void setList(List<Utils.WeatherHolder> list) {
        this.list = list;
    }

    List<Utils.WeatherHolder> list;
    Context mContext;

    LayoutInflater layoutInflater;

    public CityTabsAdapter(List<Utils.WeatherHolder> list, Context context) {
        this.list = list;
        this.mContext = context;

        layoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
     * same object as the {@link View} added to the {@link ViewPager}.
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    /**
     * Return the title of the item at {@code position}. This is important as what this method
     * returns is what is displayed in the {@link SlidingTabLayout}.
     * <p/>
     * Here we construct one using the position value, but for real application the title should
     * refer to the item's contents.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).city;
    }

    /**
     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
     * inflate a layout from the apps resources and then change the text view to signify the position.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources
        View view = layoutInflater.inflate(R.layout.pager_weather,
                container, false);

        setWeatherValue(position, view);

        container.addView(view);
        views.put(position, view);

        // Return the View
        return view;
    }

    public View getView(int position) {
        return views.get(position);
    }


    /**
     * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
     * {@link View}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        views.remove(position);
    }

    @Override
    public void notifyDataSetChanged() {
        int position = 0;
        for (int i = 0; i < views.size(); i++) {
            position = views.keyAt(i);
            View view = views.get(position);
            // Change the content of this view

            setWeatherValue(position, view);
        }
        super.notifyDataSetChanged();
    }

    /**
     * 设置天气详情
     *
     * @param position
     * @param view
     */
    private void setWeatherValue(int position, View view) {
        Weather weather = list.get(position).weather;
        if (weather != null && !TextUtils.isEmpty((weather.getRealtime().getWeather()))) {
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_refresh);
            progressBar.setVisibility(View.GONE);

            TextView textViewCity= (TextView) view.findViewById(R.id.textView_weather_city);
            textViewCity.setText(weather.getForecast().getCity());

            ImageView iconView = (ImageView) view.findViewById(R.id.icon_weather_main);
            iconView.setBackgroundResource(Utils.getWeatherTypeRsId(weather.getRealtime().getWeather(), "a"));

            TextView textViewRefreshtime = (TextView) view.findViewById(R.id.text_refreshtime);
            textViewRefreshtime.setText(DateUtil.friendly_time(weather.getToday().getDate() + " " + weather.getRealtime().getTime() + ":00"));

            TextView textViewTemp = (TextView) view.findViewById(R.id.textview_weather_main_temp);
            textViewTemp.setText(String.format("%s℃", weather.getRealtime().getTemp() + ""));

            TextView textViewShow = (TextView) view.findViewById(R.id.textview_weather_main);
            textViewShow.setText(weather.getRealtime().getWeather());

            TextView textViewAir = (TextView) view.findViewById(R.id.textview_main_airshow);
            textViewAir.setText(String.format("空气质量：%s（%s）", weather.getAqi().getAqi(), getAqiRange(weather.getAqi().getAqi())));

            TextView textViewWind = (TextView) view.findViewById(R.id.textview_main_windshow);
            textViewWind.setText(String.format("%s%s", weather.getRealtime().getWD(), weather.getRealtime().getWS()));

            TextView textViewWater = (TextView) view.findViewById(R.id.textview_main_temperature);
            textViewWater.setText(String.format("温度：%s℃~%s℃", weather.getToday().getTempMin(), weather.getToday().getTempMax()));

            LineChartData data = new LineChartData().setLines(getTempLines(weather));
            LineChartView chart = (LineChartView) view.findViewById(R.id.linechart_weather_temps);
            chart.setLineChartData(data);

            TextView detail_fl = (TextView) view.findViewById(R.id.text_weather_fl);
            detail_fl.setText(String.format("%s%s", weather.getRealtime().getWD(), weather.getRealtime().getWS()));

            TextView detail_show_fl = (TextView) view.findViewById(R.id.text_weather_detail_fl);
            detail_show_fl.setText(String.format("%s转%s\n" +
                    "风力：%s - %s级", weather.getToday().getWindDirectionStart(), weather.getToday().getWindDirectionEnd(), weather.getToday().getWindMin(), weather.getToday().getWindMax()));

            TextView detail_kq = (TextView) view.findViewById(R.id.text_weather_kq);
            detail_kq.setText(String.format("空气质量指数：%s", weather.getAqi().getAqi()));

            TextView detail_show_kq = (TextView) view.findViewById(R.id.text_weather_detail_kq);
            detail_show_kq.setText(String.format("空气质量：%s\nPM2.5：%s\nPM10：%s",
                    getAqiRange(weather.getAqi().getAqi()), weather.getAqi().getPm25(), weather.getAqi().getPm10()));

            Weather.IndexEntity detail_item;
            detail_item = getIndexDetail(weather.getIndex(), "fs");
            TextView detail_fs = (TextView) view.findViewById(R.id.text_weather_fs);
            detail_fs.setText(String.format("%s：%s", detail_item.getName(), detail_item.getIndex()));
            TextView detail_show_fs = (TextView) view.findViewById(R.id.text_weather_detail_fs);
            detail_show_fs.setText(detail_item.getDetails());

            detail_item = getIndexDetail(weather.getIndex(), "ct");
            TextView detail_cy = (TextView) view.findViewById(R.id.text_weather_cy);
            detail_cy.setText(String.format("%s：%s", detail_item.getName(), detail_item.getIndex()));
            TextView detail_show_cy = (TextView) view.findViewById(R.id.text_weather_detail_cy);
            detail_show_cy.setText(detail_item.getDetails());

            detail_item = getIndexDetail(weather.getIndex(), "yd");
            TextView detail_yd = (TextView) view.findViewById(R.id.text_weather_yd);
            detail_yd.setText(String.format("%s：%s", detail_item.getName(), detail_item.getIndex()));
            TextView detail_show_yd = (TextView) view.findViewById(R.id.text_weather_detail_yd);
            detail_show_yd.setText(detail_item.getDetails());

            detail_item = getIndexDetail(weather.getIndex(), "xc");
            TextView detail_xc = (TextView) view.findViewById(R.id.text_weather_xc);
            detail_xc.setText(String.format("%s：%s", detail_item.getName(), detail_item.getIndex()));
            TextView detail_show_xc = (TextView) view.findViewById(R.id.text_weather_detail_xc);
            detail_show_xc.setText(detail_item.getDetails());

            detail_item = getIndexDetail(weather.getIndex(), "ls");
            TextView detail_ls = (TextView) view.findViewById(R.id.text_weather_ls);
            detail_ls.setText(String.format("%s：%s", detail_item.getName(), detail_item.getIndex()));
            TextView detail_show_ls = (TextView) view.findViewById(R.id.text_weather_detail_ls);
            detail_show_ls.setText(detail_item.getDetails());

        }
    }

    private String getAqiRange(String aqi) {
        String range = "未知";
        try {
            int i = Integer.parseInt(aqi);
            if (0 <= i && i <= 50) {
                range = "优";
            } else if (51 <= i && i <= 100) {
                range = "良";
            } else if (101 <= i && i <= 150) {
                range = "轻度污染";
            } else if (151 <= i && i <= 200) {
                range = "中度污染";
            } else if (201 <= i && i < 300) {
                range = "重度污染";
            } else if (i > 300) {
                range = "严重污染";
            }
        } catch (Exception e) {
            Log.e("xxx", e.toString());
        }
        return range;
    }

    private Weather.IndexEntity getIndexDetail(List<Weather.IndexEntity> list, String code) {
        for (Weather.IndexEntity item : list) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        return null;
    }

    private List<Line> getTempLines(Weather weather) {
        List<Line> lineList = new ArrayList<>();
        List<PointValue> tmp_low = new ArrayList<>();
        List<PointValue> tmp_hight = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String strField = "temp" + i;
            try {
                Field field = weather.getForecast().getClass().getDeclaredField(strField);
                field.setAccessible(true);
                String value = (String) field.get(weather.getForecast());
                value = value.replace("℃", "");
                String[] arr = value.split("~");
                tmp_hight.add(new PointValue(i, Float.parseFloat(arr[0])));
                tmp_low.add(new PointValue(i, Float.parseFloat(arr[1])));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        tmp_low.add(1,weather.getForecast().);
        Line line1 = new Line(tmp_hight).setHasLabels(true).setColor(Color.GREEN);
        lineList.add(line1);
        lineList.add(new Line(tmp_low).setHasLabels(true).setColor(Color.GRAY));
        return lineList;
    }

}