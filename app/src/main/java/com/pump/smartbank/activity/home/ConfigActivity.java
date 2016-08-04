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

@ContentView(R.layout.activity_config)
public class ConfigActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.et_socket_ip)
    private EditText et_socket_ip;

    @ViewInject(R.id.et_socket_port)
    private EditText et_socket_port;

    @ViewInject(R.id.et_emqtt_ip)
    private EditText et_emqtt_ip;

    @ViewInject(R.id.et_emqtt_port)
    private EditText et_emqtt_port;

    @ViewInject(R.id.et_client_id)
    private EditText et_client_id;

    @ViewInject(R.id.et_emqtt_username)
    private EditText et_emqtt_username;

    @ViewInject(R.id.et_emqtt_pwd)
    private EditText et_emqtt_password;

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
                config.setEmqttIp("192.168.2.108");
                config.setEmqttPort("1883");
                config.setSocketIp("192.168.2.108");
                config.setSocketPort("7000");
                config.setEmqttUsername("default");
                config.setEmqttPassword("default");
                config.setClientId("default");
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
            et_socket_ip.setText(config.getSocketIp());
            et_socket_port.setText(config.getSocketPort());
            et_emqtt_ip.setText(config.getEmqttIp());
            et_emqtt_port.setText(config.getEmqttPort());
            et_client_id.setText(config.getClientId());
            et_emqtt_username.setText(config.getEmqttUsername());
            et_emqtt_password.setText(config.getEmqttPassword());
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
                if(!ServiceUtil.isServiceWork(this, "com.pump.smartbank.service.EmqttService")){
                    restartService();
                }
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
        if(et_socket_ip.length() == 0){
            showToast("请填写通信IP");
        }
        if(et_socket_port.length() == 0){
            showToast("请填写通信端口号");
        }
        if(et_emqtt_ip.length() == 0){
            showToast("请填写消息IP");
        }
        if(et_emqtt_port.length() == 0){
            showToast("请填写消息端口号");
        }
        if(et_client_id.length() == 0){
            showToast("请填写客户端编号");
        }
        if(et_emqtt_username.length() == 0){
            showToast("请填写消息服务用户名");
        }
        if(et_emqtt_password.length() == 0){
            showToast("请填写消息服务密码");
        }

        if(config == null){
            config = new Config();
        }
        config.setSocketIp(et_socket_ip.getText().toString());
        config.setSocketPort(et_socket_port.getText().toString());
        config.setEmqttIp(et_emqtt_ip.getText().toString());
        config.setEmqttPort(et_emqtt_port.getText().toString());
        config.setClientId(et_client_id.getText().toString());
        config.setEmqttUsername(et_emqtt_username.getText().toString());
        config.setEmqttPassword(et_emqtt_password.getText().toString());
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

    private void restartService(){
        Intent reStartServiceIntent = new Intent(this, EmqttService.class);
        startService(reStartServiceIntent);
    }

    private void test(final View view,String url){

        RequestParams params = new RequestParams("http://"+et_socket_ip.getText()+":"+et_socket_port.getText()+url);
        x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {

            @Override
            public void onSuccess(ResponseEntity response) {
                Snackbar.make(view, "Http 通信成功" + response.getResult(), Snackbar.LENGTH_LONG)
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
