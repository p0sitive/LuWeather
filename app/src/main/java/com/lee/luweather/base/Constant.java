package com.lee.luweather.base;

import android.os.Environment;

import java.io.File;

/**
 * 常量类
 * Created by Administrator on 2016-1-20.
 */
public class Constant {

    public static final String WEATHER_URL = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=CITYID";
    public static final String CITYID_STR = "CITYID";
    public static final String BAIDU_KEY = "LfR2BrC0tsKrMFuyeAg8a12k";

    public static final String LOCATION_URL = "http://api.map.baidu.com/geocoder/v2/?ak=LfR2BrC0tsKrMFuyeAg8a12k&location=PONIT&output=json";
    public static final String POINT = "POINT";

    public static final String INTENT_ACTION_GETCITY = "com.lee.luweather.getcity";
    public static final String INTENT_ACTION_ERROR = "com.lee.luweather.error";
    public static final String INTENT_ACTION_GPSFAIL = "com.lee.luweather.gpsfail";
    public static final String INTENT_ACTION_GETWEATHER = "com.lee.luweather.getweather";

    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getPath()+File.separator+"LuWeather";

    public static final String getCrashFilePath() {
        return ROOTPATH + File.separator + "crash";
    }

    public static final String getDbPath() {
        return ROOTPATH + File.separator + "data";
    }
}
