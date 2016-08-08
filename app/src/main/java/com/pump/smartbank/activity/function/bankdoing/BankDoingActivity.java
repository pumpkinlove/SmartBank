package com.pump.smartbank.activity.function.bankdoing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.adapter.BankEventAdapter;
import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.event.LoadBankDoingEvent;
import com.pump.smartbank.util.DbUtil;
import com.yalantis.phoenix.PullToRefreshView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    @ViewInject(R.id.pull_bank_event)
    private PullToRefreshView pv_bank_event;

    private BankEventAdapter bankEventAdapter;
    private List<BankEvent> bankEventList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        progressDialog.show();
        downLoadBankEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData(){
        EventBus.getDefault().register(this);
        progressDialog = new ProgressDialog(this);
        bankEventList = new ArrayList<BankEvent>();
        bankEventAdapter = new BankEventAdapter(bankEventList, this);
    }

    @Override
    protected void initView(){
        x.view().inject(this);
        progressDialog.setMessage("正在刷新...");
        tv_middleContent.setText("网点活动跟踪");
        tv_leftContent.setVisibility(View.VISIBLE);
        tv_rightContent.setText("新建活动");
        tv_rightContent.setVisibility(View.VISIBLE);

        rv_bank_event.setHasFixedSize(true);
        rv_bank_event.setLayoutManager(new LinearLayoutManager(this));
        rv_bank_event.setAdapter(bankEventAdapter);

        pv_bank_event.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pv_bank_event.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        downLoadBankEvents();
                    }
                }, 1500);
            }
        });

    }

    @Event(value={R.id.tv_leftContent,R.id.tv_rightContent},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.tv_rightContent:
                startActivityForResult(new Intent(this, NewBankEventActivity.class), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                // TODO: 2016/8/4 0004
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
            params.setCharset("utf-8");
            x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {

                @Override
                public void onSuccess(ResponseEntity response) {
                    Gson g = new Gson();
                    String reJson = response.getResult();
                    try {
                        reJson = URLDecoder.decode(reJson, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(reJson);
                    JsonArray jsonArray = null;
                    if(jsonElement.isJsonArray()){
                        jsonArray = jsonElement.getAsJsonArray();
                        Iterator it = jsonArray.iterator();
                        bankEventList.clear();
                        while(it.hasNext()){
                            JsonElement e = (JsonElement) it.next();
                            bankEventList.add(g.fromJson(e, BankEvent.class));
                        }
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(x.app(),"网络开小差啦，请稍后再试>_<",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(Callback.CancelledException cex) {
                    EventBus.getDefault().post(new LoadBankDoingEvent());
                }
                @Override
                public void onFinished() {
                    EventBus.getDefault().post(new LoadBankDoingEvent());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(LoadBankDoingEvent loadBankDoingEvent){
        bankEventAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
        pv_bank_event.setRefreshing(false);
    }

}
