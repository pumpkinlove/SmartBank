package com.pump.smartbank.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by xu.nan on 2016/7/27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initData();
    protected abstract void initView();

    protected void showToast(String content){
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT);
    }
}
