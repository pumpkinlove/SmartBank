package com.pump.smartbank.activity.function.myhelp;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.adapter.LoanItemAdapter;
import com.pump.smartbank.domain.LoanItem;
import com.pump.smartbank.fragment.LoanResultFragment;
import com.pump.smartbank.view.MyDialog;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ViewInject(R.id.lv_loan_result)
    private ListView lv_loan_result;

    private List<LoanItem> loanItemList;
    private LoanItemAdapter loanItemAdapter;

    private double loan_money = 0.0;
    private int loan_month = 0;
    private double loan_rate = 0.0;
    private int loanType;

    @ViewInject(R.id.sp_loanType)
    private Spinner sp_type;
    private ArrayAdapter<String> spAdapter;
    private static final String[] loanTypes = {"等额本息还款","等额本金还款"};

    @ViewInject(R.id.cv_cal_loan_result)
    private CardView cv_cal_loan_result;

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
        loanItemList = new ArrayList<>();
        loanItemAdapter = new LoanItemAdapter(this, loanItemList);

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

        lv_loan_result.setAdapter(loanItemAdapter);
        loanItemAdapter.notifyDataSetChanged();
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
        cv_cal_loan_result.setVisibility(View.VISIBLE);

        if(et_loan_money.length() < 1){
            return;
        }
        if(et_loan_month.length() < 1){
            return;
        }
        if(et_loan_rate.length() < 1){
            return;
        }

        loan_money = Double.valueOf(et_loan_money.getText().toString());
        loan_month = Integer.valueOf(et_loan_month.getText().toString());
        loan_rate = Double.valueOf(et_loan_rate.getText().toString());



        for(int i=1;i<=loan_month;i++){
            LoanItem item = new LoanItem();
            item.setPeriod( i + "" );

            Log.e("-------------", item.getPeriod()+"----"+item.getTotal());
            loanItemList.add(item);
        }
        loanItemAdapter.notifyDataSetChanged();

    }

    private void clearFinance(){
        cv_cal_loan_result.setVisibility(View.INVISIBLE);
        loan_money = 0.0;
        loan_month = 0;
        loan_rate = 0.0;
        et_loan_money.setText("");
        et_loan_month.setText("");
        et_loan_rate.setText("");
        loanItemList.clear();
    }

}
