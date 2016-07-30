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
import android.view.WindowManager;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.adapter.NoticeAdapter;
import com.pump.smartbank.domain.Notice;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_index)
public class IndexActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.rv_notice)
    private RecyclerView rv_notice;

    @ViewInject(R.id.rv_customer)
    private RecyclerView rv_recentVisit;

    @ViewInject(R.id.rv_watcher)
    private RecyclerView rv_recentDevelop;

    private List<Notice> noticeList;

    private Vibrator vibrator;
    private RecyclerView.Adapter noticeAdapter;
    private InformReceiver informReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        fetchCardView();
    }

    private void initData() {
        noticeList = new ArrayList<Notice>();
        noticeAdapter = new NoticeAdapter(noticeList, this);
        informReceiver = new InformReceiver();
        IntentFilter filter=new IntentFilter();
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

    }


    public class InformReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int informType = intent.getIntExtra("informType", 0);
            switch (informType){
                case 1:
                    noticeList.add((Notice)intent.getSerializableExtra("notice"));
                    noticeAdapter.notifyDataSetChanged();
                    inform();
                    break;
                case 2:
                    break;
                case 3:
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
