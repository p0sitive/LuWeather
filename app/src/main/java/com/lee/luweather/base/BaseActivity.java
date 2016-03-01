package com.lee.luweather.base;

import android.app.Activity;
import android.os.Bundle;

public  class BaseActivity extends Activity{
    protected int ResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initView(){
        setContentView(ResourceId);
    }

    protected void initData(){}

    protected void initEvent(){}
}