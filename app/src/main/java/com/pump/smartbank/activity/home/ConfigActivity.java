package com.pump.smartbank.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.response.BankEventResponse;
import com.pump.smartbank.service.EmqttService;
import com.pump.smartbank.service.TestService;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

@ContentView(R.layout.activity_config)
public class ConfigActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData(){
    }

    @Override
    protected void initView(){
        x.view().inject(this);
        tv_middleContent.setText("系统设置");
        tv_leftContent.setVisibility(View.VISIBLE);
    }

    @Event(value={R.id.tv_leftContent, R.id.ll_config_http, R.id.ll_config_emqttd, R.id.ll_config_terminal},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.ll_config_http:
                startActivity(new Intent(this, HttpConfigActivity.class));
                break;
            case R.id.ll_config_emqttd:
                startActivity(new Intent(this, EmqttdConfigActivity.class));
                break;
            case R.id.ll_config_terminal:
                startActivity(new Intent(this, TerminalConfigActivity.class));
                break;
        }
    }

}
