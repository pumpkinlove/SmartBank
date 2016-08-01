package com.pump.smartbank.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.view.MyDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    private MyDialog logutDialog;
    private MyDialog aboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    @Override
    protected void initData() {
        logutDialog = new MyDialog(this,"确定退出吗？");
        aboutDialog = new MyDialog(this,"关于",getResources().getString(R.string.about));
        aboutDialog.hideBottom();
    }

    @Override
    protected void initView(){
        x.view().inject(this);
        tv_middleContent.setText("我");
    }


    @Event(value={R.id.ll_config,R.id.ll_about,R.id.ll_update,R.id.btn_logout},type=View.OnClickListener.class)
    private void onClicked(View view){
        switch (view.getId()){
            case R.id.ll_config:
                startActivity(new Intent(HomeActivity.this, ConfigActivity.class));
                break;
            case R.id.ll_update:
                break;
            case R.id.ll_about:
                aboutDialog.show();
                break;
            case R.id.btn_logout:
                logutDialog.show();
                break;
        }
    }

}
