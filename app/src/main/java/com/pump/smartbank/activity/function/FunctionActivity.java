package com.pump.smartbank.activity.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.activity.function.analyze.AnalyzeActivity;
import com.pump.smartbank.activity.function.bankdoing.BankDoingActivity;
import com.pump.smartbank.activity.function.ciips.CiipsActivity;
import com.pump.smartbank.activity.function.myhelp.LoanCalculatorActivity;
import com.pump.smartbank.activity.function.myhelp.FinanceCalculatorActivity;
import com.pump.smartbank.activity.function.team.TeamManageActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_function)
public class FunctionActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText("功能");
    }

    @Event(value={R.id.fun_bankdoing,R.id.fun_ciips,R.id.fun_analyze,R.id.fun_team, R.id.cal_finance, R.id.cal_loan},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.fun_ciips:
                startActivity(new Intent(FunctionActivity.this, CiipsActivity.class));
                break;
            case R.id.fun_team:
                startActivity(new Intent(FunctionActivity.this, TeamManageActivity.class));
                break;
            case R.id.fun_bankdoing:
                startActivity(new Intent(FunctionActivity.this, BankDoingActivity.class));
                break;
            case R.id.fun_analyze:
                startActivity(new Intent(FunctionActivity.this, AnalyzeActivity.class));
                break;
            case R.id.cal_finance:
                startActivity(new Intent(FunctionActivity.this, FinanceCalculatorActivity.class));
                break;
            case R.id.cal_loan:
                startActivity(new Intent(FunctionActivity.this, LoanCalculatorActivity.class));
                break;
        }
    }

}
