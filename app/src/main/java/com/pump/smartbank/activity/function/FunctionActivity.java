package com.pump.smartbank.activity.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.pump.smartbank.R;
import com.pump.smartbank.activity.function.analyze.AnalyzeActivity;
import com.pump.smartbank.activity.function.bankdoing.BankDoingActivity;
import com.pump.smartbank.activity.function.ciips.CustomerInfoActivity;
import com.pump.smartbank.activity.function.myhelp.MyHelpActivity;
import com.pump.smartbank.activity.function.product.ProductActivity;
import com.pump.smartbank.activity.function.team.TeamManageActivity;
import com.pump.smartbank.comm.BaseComm;
import com.pump.smartbank.comm.GetTimeComm;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.Socket;

@ContentView(R.layout.activity_function)
public class FunctionActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    private void initData() {

    }

    private void initView() {
        x.view().inject(this);
        tv_middleContent.setText("功能");
    }

    @Event(value={R.id.fun_bankdoing,R.id.fun_ciips,R.id.fun_custom_analyze,R.id.fun_team,R.id.fun_help,R.id.fun_product},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.fun_ciips:
                startActivity(new Intent(FunctionActivity.this, CustomerInfoActivity.class));
                break;
            case R.id.fun_team:
                startActivity(new Intent(FunctionActivity.this, TeamManageActivity.class));
                break;
            case R.id.fun_product:
                startActivity(new Intent(FunctionActivity.this, ProductActivity.class));
                break;
            case R.id.fun_bankdoing:
                startActivity(new Intent(FunctionActivity.this, BankDoingActivity.class));
                break;
            case R.id.fun_custom_analyze:
                startActivity(new Intent(FunctionActivity.this, AnalyzeActivity.class));
                break;
            case R.id.fun_help:
                startActivity(new Intent(FunctionActivity.this, MyHelpActivity.class));
                break;
        }
    }
}
