package com.pump.smartbank.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by xu.nan on 2016/8/3.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        x.Ext.init(this);
        super.onCreate();
    }
}
