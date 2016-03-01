package com.lee.luweather.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lee.luweather.base.Constant;
import com.lee.luweather.model.LocalCity;

/**
 * Created by Administrator on 2016-1-21.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action =intent.getAction();
        switch (action){
            case Constant.INTENT_ACTION_GETCITY:
                LocalCity city = (LocalCity) intent.getSerializableExtra("city");

                break;
        }
    }
}
