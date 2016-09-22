package com.pump.smartbank.activity.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.gson.Gson;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.Version;
import com.pump.smartbank.util.CommonUtil;
import com.pump.smartbank.util.DbUtil;
import com.pump.smartbank.util.MyProgressCallBack;
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
import java.net.URLDecoder;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    private MyDialog logutDialog;
    private MyDialog aboutDialog;
    private MyDialog updateDialog;

    private ProgressDialog pd_check_version;

    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;

    private Config config;
    @ViewInject(R.id.pb_download)
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    @Override
    protected void initData() {
        pd_check_version = new ProgressDialog(this);
        pd_check_version.setMessage("正在检查版本...");
        pd_check_version.setCanceledOnTouchOutside(false);
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

            RequestParams params = new RequestParams("http://"+config.getHttpIp()+":"+config.getHttpPort() + "/CIIPS_A/version/lastVersion.action");
            pd_check_version.show();
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    try {
                        String reJson = response;
                        reJson = URLDecoder.decode(reJson,"utf-8");
                        Gson g = new Gson();
                        final Version lastVersion = g.fromJson(reJson,Version.class);
                        PackageManager manager = HomeActivity.this.getPackageManager();
                        PackageInfo info = manager.getPackageInfo(HomeActivity.this.getPackageName(), 0);
                        Version curVersion = new Version();
                        curVersion.setVersionCode(info.versionCode);
                        curVersion.setVersionName(info.versionName);
                        if(lastVersion.getVersionCode() <= curVersion.getVersionCode()){
                            Snackbar.make(view,"您已经更新到最新版本！",Snackbar.LENGTH_SHORT).show();
                        }else{
                            updateDialog.setMessage("当前版本： "+curVersion.getVersionName()+"\n是否升级到新版本： "+lastVersion.getVersionName()+" ?");
                            updateDialog.setPositiveText("更新");
                            updateDialog.setNegativeText("取消");
                            updateDialog.setOnNegativeListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    updateDialog.dismiss();
                                }
                            });
                            updateDialog.setOnPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    updateDialog.dismiss();
                                    downLoadNewVersion(lastVersion);
                                }
                            });
                            updateDialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    CommonUtil.MyAlert(">_< 网络不给力啊~", getFragmentManager(), "check_version_error");
                }
                @Override
                public void onCancelled(Callback.CancelledException cex) {
                }
                @Override
                public void onFinished() {
                    pd_check_version.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downLoadNewVersion(Version lastVersion){
        progressBar.setVisibility(View.VISIBLE);
        final String filepath = Environment.getExternalStorageDirectory().getPath()+"/智慧银行_"+lastVersion.getVersionName()+".apk";
        Log.e("--------------",filepath);
        String url = "http://" + config.getHttpIp() + ":" + config.getHttpPort() + "/CIIPS_A/version/downLoadLastVersion.action";
        XUtil.DownLoadFile(url, filepath,new MyProgressCallBack<File>(){
            @Override
            public void onSuccess(File result) {
                progressBar.setVisibility(View.GONE);
                Log.e("--------","onSuccess");
                File file = new File(filepath);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("--------","onError");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.e("--------","onLoading");
                if(isDownloading){
                    Log.e("--------",current+"///"+total);
                }
                progressBar.setMax((int)total);
                progressBar.setProgress((int)current);
            }

            @Override
            public void onStarted() {
                Log.e("--------","onStarted");
                super.onStarted();
            }

            @Override
            public void onWaiting() {
                Log.e("--------","onWaiting");
                super.onWaiting();
            }

            @Override
            public void onFinished() {
                Log.e("--------","onFinished");
                super.onFinished();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("--------","onCancelled");
                super.onCancelled(cex);
            }
        });
    }


}
