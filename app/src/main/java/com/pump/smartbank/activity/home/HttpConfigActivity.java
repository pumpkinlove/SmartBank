package com.pump.smartbank.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.service.EmqttService;
import com.pump.smartbank.util.DbUtil;
import com.pump.smartbank.util.ServiceUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_http_config)
public class HttpConfigActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.et_http_ip)
    private EditText et_http_ip;

    @ViewInject(R.id.et_http_port)
    private EditText et_http_port;

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
    protected void initData(){
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        dbManager = x.getDb(daoConfig);
        try {
            config = dbManager.findFirst(Config.class);
            if(config == null){
                config = new Config();
                config.setId(1);
                config.setEmqttIp("120.26.51.167");
                config.setEmqttPort("1883");
                config.setHttpIp("120.26.51.167");
                config.setHttpPort("80");
                config.setEmqttUsername("default");
                config.setEmqttPassword("default");
                config.setClientId("default");
                dbManager.saveOrUpdate(config);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(){
        x.view().inject(this);
        tv_middleContent.setText("系统设置");
        tv_leftContent.setVisibility(View.VISIBLE);
        if(config != null){
            et_http_ip.setText(config.getHttpIp());
            et_http_port.setText(config.getHttpPort());
        }
    }

    @Event(value={R.id.tv_leftContent,R.id.btn_config_confirm,R.id.btn_config_cancel,R.id.tv_testHttp,R.id.tv_testEmqtt},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.btn_config_confirm:
                saveConfig();
                if(ServiceUtil.isServiceWork(this, "com.pump.smartbank.service.EmqttService")){
                    stopService(new Intent(this, EmqttService.class));
                }
                startService( new Intent(this, EmqttService.class));
                break;
            case R.id.btn_config_cancel:
                cancelConfig();
                break;
            case R.id.tv_testHttp:
                test(view,"/CIIPS_A/test/testHttp.action");
                break;
            case R.id.tv_testEmqtt:
                test(view, "/CIIPS_A/test/testEmqtt.action");
                break;
        }
    }

    private void saveConfig(){
        if(et_http_ip.length() == 0){
            showToast("请填写通信IP");
        }
        if(et_http_port.length() == 0){
            showToast("请填写通信端口号");
        }

        if(config == null){
            config = new Config();
        }
        config.setHttpIp(et_http_ip.getText().toString());
        config.setHttpPort(et_http_port.getText().toString());
        try {
            dbManager.saveOrUpdate(config);
        } catch (DbException e) {
            e.printStackTrace();
        }

        finish();
    }

    private void cancelConfig(){
        finish();
    }

    private void test(final View view,String url){

        RequestParams params = new RequestParams("http://"+et_http_ip.getText()+":"+et_http_port.getText()+url);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String response) {
                Snackbar.make(view, "Http 通信成功" + response, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Snackbar.make(view, "Http 通信失败" + ex.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            @Override
            public void onCancelled(Callback.CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

}
