package com.lee.luweather.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Stack;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Administrator on 2016-1-20.
 */
public class MyApplication extends Application {
    /***
     * 寄存整个应用Activity
     **/
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

    Context mContext;

    LocationManager locationManager = null;

    DbManager.DaoConfig daoConfig = null;

    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppCrashHandler mCustomCrashHandler = AppCrashHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());

        locationManager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);
        daoConfig = new DbManager.DaoConfig().
                setDbDir(new File(Constant.getDbPath())).
                setDbName("city.db");
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }

    /**
     * 将Activity压入Application栈
     *
     * @param activity 将要压入栈的Activity对象
     */
    public void pushTask(Activity activity) {
        WeakReference<Activity> task = new WeakReference<>(activity);
        activitys.push(task);
    }
    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    public void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    public void removeAll() {
        // finish所有的Activity
        for (WeakReference<Activity> task : activitys) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }

    }

    /******************************************* Application中存放的Activity操作（压栈/出栈）API（开始） *****************************************/


    /******************************************* Application中存放的Activity操作（压栈/出栈）API（结束） *****************************************/


    /**
     * 获取数据库配置
     */
    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    /**
     * 定位时使用
     *
     * @return
     */
    public LocationManager getLocationManager() {
        return locationManager;
    }

}
