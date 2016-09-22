package com.pump.smartbank.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    private String version;

    @ViewInject(R.id.tv_version)
    private TextView tv_version;

    private Config config;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version = info.versionName;

            DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
            dbManager = x.getDb(daoConfig);
            Config config = dbManager.findFirst(Config.class);
            if(config == null){
                config = new Config();
                config.setEmqttIp("120.26.51.167");
                config.setEmqttPort("1883");
                config.setHttpIp("172.22.84.3");
                config.setHttpPort("8080");
                config.setEmqttUsername("default");
                config.setEmqttPassword("default");
                config.setClientId("default");
                dbManager.saveOrUpdate(config);
            }
        } catch (PackageManager.NameNotFoundException e) {
            version = "";
            e.printStackTrace();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_version.setText("版本号： "+version);
    }

    @Event(value={R.id.btn_login},type=View.OnClickListener.class)
    private void onClick(final View view){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }
}
