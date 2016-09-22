package com.pump.smartbank.activity.index;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pump.smartbank.R;
import com.pump.smartbank.adapter.CustomerAdapter;
import com.pump.smartbank.adapter.NoticeAdapter;
import com.pump.smartbank.adapter.WatchStatusAdapter;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.Notice;
import com.pump.smartbank.domain.Publish;
import com.pump.smartbank.domain.WatchStatus;
import com.pump.smartbank.domain.event.InformComeEvent;
import com.pump.smartbank.listener.MyItemListener;
import com.pump.smartbank.util.DateUtil;
import com.pump.smartbank.util.DbUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
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
    private NoticeAdapter noticeAdapter;
    private WatchStatusAdapter watchStatusAdapter;
    private CustomerAdapter customerAdapter;

    private DbManager.DaoConfig dbConfig;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
                noticeList = new ArrayList<>();
            }
            noticeAdapter = new NoticeAdapter(noticeList, this);

            WhereBuilder b2 = WhereBuilder.b();
            b2.and("comeDate","=", DateUtil.toMonthDay(new Date()));
            customerList = dbManager.selector(Customer.class).where(b2).findAll();
            if(customerList == null){
                customerList = new ArrayList<>();
            }
            customerAdapter = new CustomerAdapter(customerList, this);
            customerAdapter.setOnItemClickListener(new MyItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(IndexActivity.this, CustomerActivity.class);
                    intent.putExtra("customer", customerList.get(customerList.size() - 1 - position));
                    startActivity(intent);
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }

        watchStatusList = new ArrayList<>();
        WatchStatus w = new WatchStatus();
        w.setWorkerName("柜员甲");
        w.setPosition("1");
        w.setGood(10);
        w.setBad(0);
        w.setStatus(2);
        watchStatusList.add(w);
        watchStatusAdapter = new WatchStatusAdapter(watchStatusList, this);

    }

    private void initView() {
        x.view().inject(this);
        tv_middleContent.setText("首页");
    }


    private void fetchCardView() {
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

    //发出提醒， 震动， 声音
    private void inform(){
        //震动
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern,-1);
        beep();
        //
    }

    /**
     * 提示音
     */
    private void beep(){
        NotificationManager manger = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.defaults=Notification.DEFAULT_SOUND;
        manger.notify(1, notification);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInformCome(InformComeEvent informComeEvent) {
        try {
            String informJson = informComeEvent.getInformJson();
            Gson g = new Gson();
            Publish p = g.fromJson(informJson, Publish.class);
            if (p == null) {
                return;
            }
            String informType = p.getPubtype();
            char start = informType.charAt(0);
            if ('1'== start) {
                Notice n = new Notice();
                n.setContent("请求援助");
                n.setDate(p.getOpdate());
                n.setTime(p.getOptime());
                if ("000001".equals(p.getTerminalid())) {
                    n.setPosition("1号窗口");
                } else if ("1111".equals(p.getTerminalid())  || "0001".equals(p.getTerminalid()) ){
                    n.setPosition("填单机_"+p.getTerminalid());
                } else {
                    n.setPosition(p.getTerminalid());
                }
                dbManager.save(n);
                noticeList.add(n);
                noticeAdapter.notifyDataSetChanged();
                inform();
            } else if ('2' == start) {
                Customer c = new Customer();
                c.setBusiness(p.getPubtype().split("_")[1]);
                c.setCustomname(p.getObjname());
                c.setCardid(p.getObjid());
                c.setComeDate(p.getOpdate());
                c.setComeTime(p.getOptime());
                dbManager.save(c);
                customerList.add(c);
                customerAdapter.notifyDataSetChanged();
                inform();
            } else if ('3' == start) {
                Customer c = new Customer();
                String contentJson = p.getPubtype().split("_")[1];
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(contentJson).getAsJsonObject();
                String begintime = object.get("begintime").getAsString();
                String endtime = object.get("endtime").getAsString();
                String mobile = object.get("mobile").getAsString();
                String subdate = object.get("subdate").getAsString();
                c.setBusiness("贵宾预约("+subdate+"_"+begintime+"-"+endtime+"_"+mobile+")");
                if(p.getObjname() == null || p.getObjname().length() == 0){
                    c.setCustomname("匿名");
                }else{
                    c.setCustomname(p.getObjname());
                }

                c.setCardid(p.getObjid());
                c.setComeDate(p.getOpdate());
                c.setComeTime(p.getOptime());
                dbManager.save(c);
                customerList.add(c);
                customerAdapter.notifyDataSetChanged();
                inform();
            } else if ('4' == start) {
                watchStatusList.clear();
                WatchStatus w = new WatchStatus();
                w.setWorkerName("柜员甲");
                w.setPosition("1");
                w.setGood(13);
                w.setBad(0);
                String objid = p.getObjid();
                int status = Integer.valueOf(objid.split("_")[0]);
                w.setStatus(status);
                watchStatusList.add(w);
                watchStatusAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().register(this);
    }
}
