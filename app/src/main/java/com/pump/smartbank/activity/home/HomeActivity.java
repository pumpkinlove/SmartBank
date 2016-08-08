package com.pump.smartbank.activity.home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.google.gson.Gson;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.Version;
import com.pump.smartbank.util.DbUtil;
import com.pump.smartbank.util.MyCallBack;
import com.pump.smartbank.util.XUtil;
import com.pump.smartbank.view.MyDialog;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    private MyDialog logutDialog;
    private MyDialog aboutDialog;
    private MyDialog updateDialog;

    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;

    private Config config;



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
        updateDialog = new MyDialog(this);
        aboutDialog.hideBottom();
        daoConfig = DbUtil.getDaoConfig();
        dbManager = x.getDb(daoConfig);
        try {
            config = dbManager.findFirst(Config.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
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
                checkUpdate(view);
                break;
            case R.id.ll_about:
                aboutDialog.show();
                break;
            case R.id.btn_logout:
                logutDialog.show();
                break;
        }
    }

    private void checkUpdate(final View view){
        try {

            RequestParams params = new RequestParams("http://"+config.getSocketIp()+":"+config.getSocketPort() + "/CIIPS_A/version/lastVersion.action");

            x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {
                @Override
                public void onSuccess(ResponseEntity response) {
                    try {
                        String reJson = response.getResult();
                        reJson = URLDecoder.decode(reJson,"utf-8");
                        Gson g = new Gson();
                        Version lastVersion = g.fromJson(reJson,Version.class);
                        PackageManager manager = HomeActivity.this.getPackageManager();
                        PackageInfo info = manager.getPackageInfo(HomeActivity.this.getPackageName(), 0);
                        Version curVersion = new Version();
                        curVersion.setVersionCode(info.versionCode);
                        curVersion.setVersionName(info.versionName);
                        if(lastVersion.getVersionCode() <= curVersion.getVersionCode()){
                            Snackbar.make(view,"您已经更新到最新版本！",Snackbar.LENGTH_SHORT).show();
                        }else{
                            downLoadNewVersion();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }
                @Override
                public void onCancelled(Callback.CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void downLoadNewVersion(){
        String filepath = "";
        String url = "http://" + config.getSocketIp() + ":" + config.getSocketPort() + "/CIIPS_A/version/downLoadLastVersion.action";
        XUtil.DownLoadFile(url, filepath,new MyCallBack<File>(){
            @Override
            public void onSuccess(File result) {
                super.onSuccess(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);

            }
        });
    }

}
