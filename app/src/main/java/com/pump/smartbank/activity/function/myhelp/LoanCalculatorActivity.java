package com.pump.smartbank.activity.function.myhelp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.fragment.LoanResultFragment;
import com.pump.smartbank.view.MyDialog;

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

    @ViewInject(R.id.et_loan_money)
    private EditText et_loan_money;

    @ViewInject(R.id.et_loan_month)
    private EditText et_loan_month;

    @ViewInject(R.id.et_loan_rate)
    private EditText et_loan_rate;

    private double loan_money = 0.0;
    private double loan_month = 0.0;
    private double loan_rate = 0.0;
    private int loanType;

    private MyDialog resultDialog;
    private FrameLayout dFrameLayout;
    private LoanResultFragment loanResultFragment;

    @ViewInject(R.id.sp_loanType)
    private Spinner sp_type;
    private ArrayAdapter<String> spAdapter;

    private static final String[] loanTypes = {"等额本息还款","等额本金还款"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData() {
        spAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, loanTypes);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultDialog = new MyDialog(this, "贷款计算结果", "");
        resultDialog.hideContent();
        resultDialog.showFrameLayout();
        resultDialog.hideBottom();

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
//        if(et_loan_money.length() < 1){
//            return;
//        }
//        if(et_loan_month.length() < 1){
//            return;
//        }
//        if(et_loan_rate.length() < 1){
//            return;
//        }

        resultDialog.show();
    }

    private void clearFinance(){
        loan_money = 0.0;
        loan_month = 0.0;
        loan_rate = 0.0;
        et_loan_money.setText("");
        et_loan_month.setText("");
        et_loan_rate.setText("");
    }

}
