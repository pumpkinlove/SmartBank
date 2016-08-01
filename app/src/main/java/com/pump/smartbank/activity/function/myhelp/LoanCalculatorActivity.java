package com.pump.smartbank.activity.function.myhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;

@ContentView(R.layout.activity_loan_calculator)
public class LoanCalculatorActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.et_finace_money)
    private EditText et_finance_money;

    @ViewInject(R.id.et_finance_year)
    private EditText et_finance_year;

    @ViewInject(R.id.et_finance_rate)
    private EditText et_finance_rate;

    @ViewInject(R.id.tv_finance_result)
    private TextView tv_finance_result;

    private double f_money = 0.0;
    private double f_year = 0.0;
    private double f_rate = 0.0;
    private double f_result_earn = 0.0;
    private double f_result_total = 0.0;

    private int loanType;

    @ViewInject(R.id.sp_loanType)
    private Spinner sp_type;
    private ArrayAdapter<String> spAdapter;

    private static final String[] m={"等额本息还款","等额本金还款"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData() {
        spAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText("贷款计算器");
        tv_leftContent.setVisibility(View.VISIBLE);
        sp_type.setAdapter(spAdapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loanType = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Event(value={R.id.tv_leftContent,R.id.btn_calculate,R.id.btn_clear },type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.btn_calculate:
                calculateFinance();
                break;
            case R.id.btn_clear:
                clearFinance();
                break;
        }
    }

    private void calculateFinance(){
        if(et_finance_money.length() < 1){
            return;
        }
        if(et_finance_year.length() < 1){
            return;
        }
        if(et_finance_rate.length() < 1){
            return;
        }
        f_money = Double.valueOf(et_finance_money.getText().toString());
        f_year = Double.valueOf(et_finance_year.getText().toString());
        f_rate = Double.valueOf(et_finance_rate.getText().toString());
        f_result_total = Math.pow(( 1 + f_rate/100), f_year) * f_money ;
        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        f_result_earn = f_result_total - f_money;
        String htmlStr =
                "<html><body>" +
                        "<p><font color=\"#aabb00\">" + f_year + "</p>" +
                        " 年后，收益 "+
                        "<p><font color=\"#00bbaa\">" + dcmFmt.format(f_result_earn) + "</p>" +
                        " 元，合计 "+
                        "<p><font color=\"#00bbaa\">" + dcmFmt.format(f_result_total) + "</p>" +
                        " 元 " +
                        "</body></html>";
        tv_finance_result.setText(Html.fromHtml(htmlStr));
    }

    private void clearFinance(){
        f_money = 0.0;
        f_year = 0.0;
        f_rate = 0.0;
        f_result_earn = 0.0;
        f_result_total = 0.0;
        et_finance_money.setText("");
        et_finance_year.setText("");
        et_finance_rate.setText("");
        tv_finance_result.setText("");
    }
}
