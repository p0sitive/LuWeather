package com.lee.luweather.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lee.luweather.R;

import java.util.jar.Attributes;

/**
 * 展示iconfont的TextView
 * Created by Administrator on 2016-1-20.
 */
public class IconView extends TextView {
    Context mContext;

    public IconView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public IconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                mContext.getResources().getString(R.string.iconfont_name));
        this.setTypeface(tf);
    }
}
