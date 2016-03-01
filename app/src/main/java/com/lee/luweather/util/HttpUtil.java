package com.lee.luweather.util;


import com.lee.luweather.base.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 简单封装一下网络请求
 * Created by Administrator on 2016-1-21.
 */
public class HttpUtil {

    public enum RequireType {
        REQUIRE_CITY, REQUIRE_WEATHER
    }

    RequestParams params;

    static HttpUtil instance;

    private HttpUtil() {
    }

    /**
     * 网络请求的单例
     *
     * @return
     */
    public HttpUtil getInstance() {
        if (instance == null) {
            instance = new HttpUtil();
        }
        return instance;
    }

    /**
     * 根据类型获取结果的json字符串
     * @param type
     * @return
     */
    public String getResultJsonString(RequireType type) {
        String url = "";
        if (type == RequireType.REQUIRE_CITY)
            url = Constant.LOCATION_URL;
        else if (type == RequireType.REQUIRE_WEATHER) {
            url = Constant.WEATHER_URL;
        }
        if (!url.equals("")) {
            params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback() {
                @Override
                public void onSuccess(Object result) {

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
        }
        return "";
    }
}
