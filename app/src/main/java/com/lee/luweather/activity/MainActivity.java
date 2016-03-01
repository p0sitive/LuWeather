package com.lee.luweather.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lee.luweather.R;
import com.lee.luweather.adapter.CityTabsAdapter;
import com.lee.luweather.base.Constant;
import com.lee.luweather.base.MyApplication;
import com.lee.luweather.model.CityWeather;
import com.lee.luweather.model.SelectCity;
import com.lee.luweather.model.Weather;
import com.lee.luweather.util.DateUtil;
import com.lee.luweather.util.DbHelper;
import com.lee.luweather.util.NetworkUtils;
import com.lee.luweather.util.StatusBarUtils;
import com.lee.luweather.util.Utils;
import com.lee.luweather.view.SlidingTabLayout;

import org.xutils.DbManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    SlidingTabLayout mSlidingTabLayout;
    ViewPager mViewPager;
    List<Utils.WeatherHolder> cityList;
    CityTabsAdapter cityTabsAdapter;
    MyBroadcastReceiver myReceiver;
    Toolbar mToolbar;
    Context mContext;
    MyApplication application;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = ((MyApplication) getApplication());
        application.pushTask(this);
        mContext = this;

        initData();

        initView();


    }

    private void initData() {
        Utils.getIntence(this);
        File dir = new File(Constant.getDbPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String dbFilePath = Constant.getDbPath() + File.separator + "city.db";
        File file = new File(dbFilePath);
        if (!file.exists()) {
            AssetManager am = this.getAssets();
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = am.open("city.db");
                fos = new FileOutputStream(dbFilePath);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer);
                }

            } catch (Exception e) {
                Log.i("xxx", e.toString());
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        getLocalCity();
        getSelectCity();
    }

    /**
     * 获取数据库中存储的数据
     */
    private void getSelectCity() {
        cityList = new ArrayList<>();
        List<SelectCity> list = DbHelper.getInstance().getAllSelectCity();
        if (list == null || list.size() == 0) {
            return;
        }
        Log.i("xxx", "城市列表长度：" + list.size());
        for (SelectCity selectCity : list) {
            CityWeather cityWeather = DbHelper.getInstance().getLastCityWeather(selectCity.getCityname());
            if (cityWeather != null && !TextUtils.isEmpty(cityWeather.getWeather())) {
                Utils.WeatherHolder holder = new Utils.WeatherHolder();
                holder.city = selectCity.getCityname();
                holder.weather = new Gson().fromJson(cityWeather.getWeather(), Weather.class);
                cityList.add(holder);
            }
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorMainDark));
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(7); // tabcachesize (=tabcount for better performance)
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        // use own style rules for tab layout
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.tab_indicator_color));
        mSlidingTabLayout.setDistributeEvenly(true);
        refreshTabs();

        StatusBarUtils.setColorForDrawerLayout(this, mDrawerLayout, res.getColor(R.color.colorMainDark));
        // Tab events
        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                    Log.i("xxx", "scroll position: " + position + " , offset: " + positionOffset);
                    if (null != swipeRefreshLayout && swipeRefreshLayout.isEnabled()) {
                        //swipeRefreshLayout.setEnabled(false);
                        Log.i("xxx", "swipeRefreshLayout is not enabled");

                    }

                }

                @Override
                public void onPageSelected(int position) {
                    Log.i("xxx", "selected position: " + position);
                    if (null != swipeRefreshLayout && !swipeRefreshLayout.isEnabled()) {
                        //swipeRefreshLayout.setEnabled(true);
                        Log.i("xxx","swipeRefreshLayout is enabled");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        LinearLayout navButton_SelectCity = (LinearLayout) findViewById(R.id.txtNavButton_selectCity);
        navButton_SelectCity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CitySelectActivity.class);
                mContext.startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        LinearLayout navButton_CityManager = (LinearLayout) findViewById(R.id.txtNavButton_CityManager);
        navButton_CityManager.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CityManagerActivity.class);
                mContext.startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.weather_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.swipe_color_1, R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCurrentCityWeather();
            }
        });


    }

    private void refreshTabs() {
        cityTabsAdapter = new CityTabsAdapter(cityList, MainActivity.this);
        mViewPager.setAdapter(cityTabsAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.INTENT_ACTION_ERROR);
        filter.addAction(Constant.INTENT_ACTION_GETCITY);
        filter.addAction(Constant.INTENT_ACTION_GPSFAIL);
        filter.addAction(Constant.INTENT_ACTION_GETWEATHER);
        registerReceiver(myReceiver, filter);

        getSelectCity();
        refreshTabs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "Setting is checked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_refresh:
                if (mViewPager.getChildCount() == 0) {
                    Utils.showToast(MainActivity.this, "Please Select City!");
                    return true;
                }
                refreshCurrentCityWeather();
                return true;
            case R.id.action_reset:
                DbHelper.getInstance().deleteAllCity();
                //getSelectCity();
                cityList.clear();
                refreshTabs();
                cityTabsAdapter.notifyDataSetChanged();
                Utils.showToast(MainActivity.this, "重置成功");

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void refreshCurrentCityWeather() {
        Utils.WeatherHolder holder = cityTabsAdapter.getList().get(mViewPager.getCurrentItem());
        Utils.getWeatherByCityName(holder.city);
        if (null != swipeRefreshLayout || swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout.closeDrawers();
        super.onBackPressed();
    }

    public String getLocalCity() {
        Utils.getIntence(this).getLocal(application.getLocationManager());
        return "";
    }

    /**
     * 判断当前选择的城市是否存在
     *
     * @param cityName
     * @returnStatusBarUtils
     */
    boolean isExistCity(String cityName) {
        boolean isExist = false;
        List<Utils.WeatherHolder> list = cityTabsAdapter.getList();
        if (list != null || list.size() > 0) {
            for (Utils.WeatherHolder temp : list) {
                if (temp.city.equals(cityName)) {
                    isExist = true;
                    break;
                }
            }
        } else {
            isExist = true;
        }
        return isExist;
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String cityName = "";
            Utils.WeatherHolder holder;
            switch (action) {
                case Constant.INTENT_ACTION_GETCITY:
                    cityName = intent.getStringExtra("city");
//                    String cityId = intent.getStringExtra("cityid");
                    if (!isExistCity(cityName)) {
                        holder = new Utils.WeatherHolder();
                        holder.city = cityName;
                        holder.weather = null;
                        cityList.add(holder);
                        if (!NetworkUtils.getInstance(MainActivity.this).isConnected()) {
                            NetworkUtils.getInstance(MainActivity.this).validateNetWork();
                            break;
                        }
                        if (!TextUtils.isEmpty(cityName)) {
                            Utils.getWeatherByCityName(cityName);
                        }
                    }
                    if (cityTabsAdapter != null) {
                        refreshTabs();
                        cityTabsAdapter.notifyDataSetChanged();
                    }
                    break;
                case Constant.INTENT_ACTION_GETWEATHER:
                    cityName = intent.getStringExtra("city");
                    String weather = intent.getStringExtra("weather");
                    Log.i("xxx_receiver", weather);
                    boolean isAdd = false;
                    CityWeather cityWeather = new CityWeather();
                    cityWeather.setDate(DateUtil.getNow());
                    cityWeather.setWeather(weather);
                    cityWeather.setCityname(cityName);
                    int index = 0;
                    Weather wt = new Gson().fromJson(weather, Weather.class);
                    if (cityTabsAdapter.getList() != null) {
                        for (Utils.WeatherHolder temp : cityTabsAdapter.getList()) {
                            if (temp.city.equals(cityName)) {
                                temp.weather = wt;
                                DbHelper.getInstance().saveCityWeather(cityWeather);
                                isAdd = true;
                                break;
                            }
                            index++;
                        }
                        if (!isAdd) {
                            holder = new Utils.WeatherHolder(cityName, wt);
                            cityTabsAdapter.getList().add(holder);
                            DbHelper.getInstance().saveCityWeather(cityWeather);
                        }
                    }
                    cityTabsAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(index, true);
                    break;
            }
        }
    }

}
