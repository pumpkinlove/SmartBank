package com.pump.smartbank.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
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
        }
    }

    @Event(value={R.id.tv_leftContent,R.id.btn_config_confirm,R.id.btn_config_cancel},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.btn_config_confirm:
                saveConfig();
                break;
            case R.id.btn_config_cancel:
                cancelConfig();
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
        if(config == null){
            config = new Config();
        }
        config.setSocketIp(et_socket_ip.getText().toString());
        config.setSocketPort(et_socket_port.getText().toString());
        config.setEmqttIp(et_emqtt_ip.getText().toString());
        config.setEmqttPort(et_emqtt_port.getText().toString());
        config.setClientId(et_client_id.getText().toString());

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
}
