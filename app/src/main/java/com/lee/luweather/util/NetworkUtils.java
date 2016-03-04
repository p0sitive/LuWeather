package com.lee.luweather.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 基于静态内部类实现的单例，保证线程安全的网络信息工具类 <per> 使用该工具类之前，记得在AndroidManifest.xml添加权限许可 <xmp>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * </xmp> </per>
 * <p/>
 * 安卓判断网络状态，只需要在相应的Activity的相关方法（onCreat/onResum）调用一行代码即可
 * NetworkUtils.getInstance(getActivity()).validateNetWork();
 *
 * @author Administrator
 */
public class NetworkUtils {
    public final static String NETWORK_CMNET = "CMNET";
    public final static String NETWORK_CMWAP = "CMWAP";
    public final static String NETWORK_WIFI = "WIFI";
    public final static String TAG = "NetworkUtils";
    private static NetworkInfo networkInfo = null;
    private Context mContext = null;
    AlertDialog mAlertDialog = null;

    private volatile static NetworkUtils instance;

    private NetworkUtils() {
    }

    private NetworkUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 获取NetworkUtils的对象
     *
     * @return
     */
    public static NetworkUtils getInstance(Context context) {
        if (null == instance) {
            synchronized (NetworkUtils.class) {
                if (null == instance) {
                    instance = new NetworkUtils(context);
                }
            }
        }
        return instance;
    }

    public NetworkUtils init(Context context) {
        this.mContext = context.getApplicationContext();
        return this;
    }

    /**
     * 判断网络连接是否可用
     *
     * @return
     */
    public boolean isAvailable() {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return false;
        }
        networkInfo = manager.getActiveNetworkInfo();
        if (null == networkInfo || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 判断网络连接是否可用
     *
     * @return
     */
    public boolean isConnected() {
        if (!isAvailable()) {
            return false;
        }
        if (!networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    /**
     * 检查当前环境网络是否可用，不可用跳转至开启网络界面,不设置网络强制关闭当前Activity
     */
    public void validateNetWork() {
        if (isConnected()) {
            Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("网络设置");
            builder.setMessage("网络设置不可用，现在设置网络？");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity) mContext)
                                    .startActivityForResult(
                                            new Intent(
                                                    android.provider.Settings.ACTION_SETTINGS),
                                            which);
                        }
                    });
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            if (null == mAlertDialog) {
                mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        }
    }

    /**
     * 获取网络连接信息</br> 无网络：</br> WIFI网络：WIFI</br> WAP网络：CMWAP</br>
     * NET网络：CMNET</br>
     *
     * @return
     */
    public String getNetworkType() {
        if (isConnected()) {
            int type = networkInfo.getType();
            if (ConnectivityManager.TYPE_MOBILE == type) {
                Log.i(TAG,
                        "networkInfo.getExtraInfo()-->"
                                + networkInfo.getExtraInfo());
                if (NETWORK_CMWAP.equals(networkInfo.getExtraInfo()
                        .toLowerCase())) {
                    return NETWORK_CMWAP;
                } else {
                    return NETWORK_CMNET;
                }
            } else if (ConnectivityManager.TYPE_WIFI == type) {
                return NETWORK_WIFI;
            }
        }

        return "";
    }
}
