package com.pump.smartbank.activity.function.bankdoing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.adapter.BankEventAdapter;
import com.pump.smartbank.comm.BankDoingComm;
import com.pump.smartbank.comm.BaseComm;
import com.pump.smartbank.comm.GetTimeComm;
import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.config.DbConfigs;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ContentView(R.layout.activity_bank_doing)
public class BankDoingActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;
    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;
    @ViewInject(R.id.tv_rightContent)
    private TextView tv_rightContent;

    @ViewInject(R.id.rv_bank_event)
    private RecyclerView rv_bank_event;

    private BankEventAdapter bankEventAdapter;
    private List<BankEvent> bankEventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        initCamera();
    }

    @Override
    protected void initData(){
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        DbManager dbManager =  x.getDb(daoConfig);
        try {
            bankEventList = dbManager.findAll(BankEvent.class);
            if(bankEventList == null){
                bankEventList = new ArrayList<>();
                bankEventList.add(new BankEvent("sdsd",null,"fasf","dasd"));
            }
            bankEventAdapter = new BankEventAdapter(bankEventList, this);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(){
        x.view().inject(this);
        tv_middleContent.setText("网点活动跟踪");
        tv_leftContent.setVisibility(View.VISIBLE);
        tv_rightContent.setText("新建活动");
        tv_rightContent.setVisibility(View.VISIBLE);

        rv_bank_event.setHasFixedSize(true);
        rv_bank_event.setLayoutManager(new LinearLayoutManager(this));
        rv_bank_event.setAdapter(bankEventAdapter);

    }

    private void initCamera(){

    }

    @Event(value={R.id.tv_leftContent,R.id.tv_rightContent},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.tv_rightContent:
//                startActivityForResult(new Intent(this, NewBankEventActivity.class), 1);
                downLoadBankEvents();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                // TODO: 2016/8/2
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void downLoadBankEvents(){
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        DbManager dbManager = x.getDb(daoConfig);
        try {
            Config config = dbManager.findFirst(Config.class);

            RequestParams params = new RequestParams("http://"+config.getSocketIp()+":"+config.getSocketPort() + "/CIIPS_A/bankdoing/findall.action");
            x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {

                @Override
                public void onSuccess(ResponseEntity response) {
                    Gson g = new Gson();
                    String reJson = response.getResult();
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(reJson);
                    JsonArray jsonArray = null;
                    if(jsonElement.isJsonArray()){
                        jsonArray = jsonElement.getAsJsonArray();
                    }
                    Iterator it = jsonArray.iterator();
                    if(it.hasNext()){
                        JsonElement e = (JsonElement) it.next();
                        bankEventList.add(g.fromJson(e, BankEvent.class));
                    }
                    bankEventAdapter = new BankEventAdapter(bankEventList, BankDoingActivity.this);
                    bankEventAdapter.notifyDataSetChanged();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
