package com.pump.smartbank.activity.index;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.util.DateUtil;
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

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;

import Decoder.BASE64Decoder;

@ContentView(R.layout.activity_customer)
public class CustomerActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;
    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.tv_c_name)
    private TextView tv_c_name;
    @ViewInject(R.id.tv_c_gender)
    private TextView tv_c_gender;
    @ViewInject(R.id.tv_c_age)
    private TextView tv_c_age;
    @ViewInject(R.id.tv_c_birthday)
    private TextView tv_c_birthday;
    @ViewInject(R.id.tv_c_idcardno)
    private TextView tv_c_idcardno;
    @ViewInject(R.id.tv_c_address)
    private TextView tv_c_address;
    @ViewInject(R.id.tv_c_job)
    private TextView tv_c_job;
    @ViewInject(R.id.tv_c_nation)
    private TextView tv_c_nation;
    @ViewInject(R.id.tv_c_race)
    private TextView tv_c_race;
    @ViewInject(R.id.iv_c_photo)
    private ImageView iv_c_photo;
    @ViewInject(R.id.tv_c_telphone)
    private TextView tv_c_telphone;

    private Customer customer;
    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        downLoadCustomer(customer.getCustomname());
    }

    @Override
    protected void initData() {
        customer = (Customer) getIntent().getSerializableExtra("customer");
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText("客户信息");
        tv_leftContent.setVisibility(View.VISIBLE);

    }

    @Event(value={R.id.tv_leftContent},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
        }
    }

    private void downLoadCustomer(String customerName){

        try {
            daoConfig = DbUtil.getDaoConfig();
            dbManager = x.getDb(daoConfig);
            Config config = dbManager.findFirst(Config.class);

            RequestParams params = new RequestParams("http://"+config.getHttpIp()+":"+config.getHttpPort() + "/CIIPS_A/customer/select.action");

            params.setCharset("utf-8");
            params.addParameter("customname",customerName);

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    try {
                        String reJson = response;
                        reJson = URLDecoder.decode(reJson,"utf-8");
                        Gson g = new Gson();
                        customer =  g.fromJson(reJson,Customer.class);
                        fetchCustomer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }
                @Override
                public void onCancelled(Callback.CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void fetchCustomer(){

        String b = customer.getBirthday();


        tv_c_name.setText(customer.getCustomname());
        tv_c_age.setText(DateUtil.getAge(customer.getBirthday())+" 周岁");
        tv_c_gender.setText(customer.getSex());
        tv_c_birthday.setText(customer.getBirthday());
        tv_c_idcardno.setText(customer.getCardid().substring(0,14)+"****");
        tv_c_address.setText(customer.getAddress());
        tv_c_job.setText(customer.getCareer());
        tv_c_nation.setText(customer.getNation());
        tv_c_race.setText(customer.getMz());
        tv_c_telphone.setText(customer.getTelphone());
        byte[] bitmapArray = new byte[0];
        try {
            bitmapArray = (new BASE64Decoder()).decodeBuffer(customer.getCardpic());
            iv_c_photo.setImageBitmap(BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
