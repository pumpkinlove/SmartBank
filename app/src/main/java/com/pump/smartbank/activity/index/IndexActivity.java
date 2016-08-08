package com.pump.smartbank.activity.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pump.smartbank.R;
import com.pump.smartbank.adapter.CustomerAdapter;
import com.pump.smartbank.adapter.NoticeAdapter;
import com.pump.smartbank.adapter.WatchStatusAdapter;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.Notice;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.WatchStatus;
import com.pump.smartbank.listener.MyItemListener;
import com.pump.smartbank.util.DateUtil;
import com.pump.smartbank.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.config.DbConfigs;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContentView(R.layout.activity_index)
public class IndexActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.rv_notice)
    private RecyclerView rv_notice;

    @ViewInject(R.id.rv_customer)
    private RecyclerView rv_customer;

    @ViewInject(R.id.rv_watcher)
    private RecyclerView rv_watchStatus;

    private List<Notice> noticeList;
    private List<WatchStatus> watchStatusList;
    private List<Customer> customerList;

    private Vibrator vibrator;
    private RecyclerView.Adapter noticeAdapter;
    private RecyclerView.Adapter watchStatusAdapter;
    private CustomerAdapter customerAdapter;

    private InformReceiver informReceiver;
    private DbManager.DaoConfig dbConfig;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        fetchCardView();
    }

    private void initData() {
        dbConfig = DbUtil.getDaoConfig();
        dbManager = x.getDb(dbConfig);
        try {
            WhereBuilder b1 = WhereBuilder.b();
            b1.and("date","=", DateUtil.toMonthDay(new Date()));
            noticeList = dbManager.selector(Notice.class).where(b1).findAll();
            if(noticeList == null){
                noticeList = new ArrayList<Notice>();
            }
            noticeAdapter = new NoticeAdapter(noticeList, this);

            WhereBuilder b2 = WhereBuilder.b();
            b2.and("comeDate","=", DateUtil.toMonthDay(new Date()));
            customerList = dbManager.selector(Customer.class).where(b2).findAll();
            if(customerList == null){
                customerList = new ArrayList<Customer>();
            }
            customerAdapter = new CustomerAdapter(customerList, this);
            customerAdapter.setOnItemClickListener(new MyItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(IndexActivity.this, CustomerActivity.class);
                    intent.putExtra("customer", customerList.get(position));
                    startActivity(intent);
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }

        watchStatusList = new ArrayList<WatchStatus>();
        watchStatusAdapter = new WatchStatusAdapter(watchStatusList, this);

        informReceiver = new InformReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.test");
        registerReceiver(informReceiver,filter);

    }

    private void initView() {
        x.view().inject(this);
        tv_middleContent.setText("首页");
    }


    private void fetchCardView(){
        rv_notice.setHasFixedSize(true);
        rv_notice.setLayoutManager(new LinearLayoutManager(this));
        rv_notice.setAdapter(noticeAdapter);

        rv_watchStatus.setHasFixedSize(true);
        rv_watchStatus.setLayoutManager(new LinearLayoutManager(this));
        rv_watchStatus.setAdapter(watchStatusAdapter);

        rv_customer.setHasFixedSize(true);
        rv_customer.setLayoutManager(new LinearLayoutManager(this));
        rv_customer.setAdapter(customerAdapter);
    }

    public class InformReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int informType = intent.getIntExtra("informType", 0);
            switch (informType){
                case 1:
                    try {
                        Notice newNotice = (Notice)intent.getSerializableExtra("notice");
                        dbManager.save(newNotice);
                        noticeList.add(newNotice);
                        noticeAdapter.notifyDataSetChanged();
                        inform();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    WatchStatus watchStatus = (WatchStatus)intent.getSerializableExtra("watchStatus");
                    watchStatusList.add(watchStatus);
                    watchStatusAdapter.notifyDataSetChanged();
                    inform();
                    break;
                case 3:
                    try {
                        Customer customer = new Customer();
                        String customname = intent.getStringExtra("customer");
                        customer.setCustomname(customname);
                        customer.setComeTime(DateUtil.toHourMinString(new Date()));
                        customer.setComeDate(DateUtil.toMonthDay(new Date()));
                        customerList.add(customer);
                        dbManager.save(customer);
                        customerAdapter.notifyDataSetChanged();
                        inform();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    String testReMessage = intent.getStringExtra("testReMessage");
                    Toast.makeText(IndexActivity.this,testReMessage,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    //发出提醒， 震动， 声音
    private void inform(){
        //震动
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern,-1);

        //
    }

}
