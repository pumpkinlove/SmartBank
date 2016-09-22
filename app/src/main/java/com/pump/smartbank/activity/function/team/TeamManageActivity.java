package com.pump.smartbank.activity.function.team;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.adapter.QueueInfoAdapter;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.Notice;
import com.pump.smartbank.domain.Publish;
import com.pump.smartbank.domain.QueueInfo;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.WaitNum;
import com.pump.smartbank.domain.WatchStatus;
import com.pump.smartbank.domain.event.LoadBankDoingEvent;
import com.pump.smartbank.domain.event.LoadWaitNumEvent;
import com.pump.smartbank.util.CommonUtil;
import com.pump.smartbank.util.XUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_team_manage)
public class TeamManageActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    private List<QueueInfo> queueInfoList;
    private QueueInfoAdapter queueAdapter;

    @ViewInject(R.id.rv_queue_info)
    private RecyclerView rv_queue;

    private List<Integer> waitNumList;
    @ViewInject(R.id.tv_total)
    private TextView tv_total;
    @ViewInject(R.id.tv_workercurnum)
    private TextView tv_curnum;
    @ViewInject(R.id.tv_businesstype)
    private TextView tv_businesstype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        x.view().inject(this);
        initData();
        initView();
        loadQueueId(null);

    }

    @Override
    protected void initData() {
        waitNumList = new ArrayList<>();
        queueInfoList = new ArrayList<>();
        queueAdapter = new QueueInfoAdapter(queueInfoList, this);

        WindowReceiver wReceiver = new WindowReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.test");
        registerReceiver(wReceiver,filter);
    }

    @Override
    protected void initView() {
        tv_middleContent.setText("队列管理");
        tv_leftContent.setVisibility(View.VISIBLE);

        rv_queue.setLayoutManager(new LinearLayoutManager(this));
        rv_queue.setAdapter(queueAdapter);

    }

    @Event(value={R.id.tv_leftContent },type=View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_leftContent:
                finish();
                break;
        }
    }

    @Event(value = R.id.tv_middleContent)
    private void loadQueueId(View view){
        Log.e("---------","========loadQueueId");
        String url = "http://www.zzjrfw.cn/fbtj/webChat/queueInfo";
        Map<String ,String> param = new HashMap<>();
        param.put("organid","1001");

        XUtil.Get(url,param,new Callback.CommonCallback<String>(){

            @Override
            public void onSuccess(String result) {
                Log.e("result=====", "onSuccess");
                Gson g = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(result);
                JsonArray jsonArray = null;
                if(jsonElement.isJsonArray()){
                    jsonArray = jsonElement.getAsJsonArray();
                    Iterator it = jsonArray.iterator();
                    queueInfoList.clear();
                    while(it.hasNext()){
                        JsonElement e = (JsonElement) it.next();
                        QueueInfo q = g.fromJson(e, QueueInfo.class);
                        queueInfoList.add(q);
                        loadWaitNum(q.getQueueid());
                        Log.e("-----",queueInfoList.size()+"");
                    }
                    queueAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("ex=====onError", ex.toString());
                CommonUtil.MyAlert(">_< 网络开小差~",getFragmentManager(),"queue_error");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("ex=====", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.e("ex=====", "onFinished");
            }
        });
    }

    private void loadWaitNum(String queueid){
        Log.e("---loadWaitNum",queueid);
        String url = "http://www.zzjrfw.cn/fbtj/webChat/queueWaitNum";
        Map<String ,String> param = new HashMap<>();
        param.put("organid","1001");
        param.put("queueid",queueid);
        XUtil.Get(url, param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("----------loadWaitNum","onSuccess");
                ResponseEntity<WaitNum> resp = ResponseEntity.fromJson(result,WaitNum.class);
                for(int i=0; i<queueInfoList.size(); i++){
                    if(queueInfoList.get(i).getQueueid().equals(resp.getData().getQueueid())){
                        queueInfoList.get(i).setOrganid(resp.getData().getWaitnum());
                        waitNumList.add(Integer.valueOf(resp.getData().getWaitnum()));
                        queueAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("----------loadWaitNum","onError"+ex.toString());
                CommonUtil.MyAlert(">_< 网络开小差~",getFragmentManager(),"queue_error");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("----------loadWaitNum","onCancelled");
            }

            @Override
            public void onFinished() {
              EventBus.getDefault().post(new LoadWaitNumEvent());
                Log.e("----------loadWaitNum","onFinished");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotalWaitNum(LoadWaitNumEvent event){
        int x = 0;
        for(int i=0; i<waitNumList.size(); i++){
            x+=waitNumList.get(i);
        }
        tv_total.setText(x+"");
    }

    public class WindowReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Publish p = (Publish) intent.getSerializableExtra("publish");
                if(p == null){
                    return;
                }
                String informType = p.getPubtype();
                char start = informType.charAt(0);
                if('1'== start){
                }else if('2'== start){
                }else if('3' == start){
                }else if('4' == start){
                    String objid = p.getObjid();
                    Log.e("----windowr", ""+ objid);

                    switch (Integer.valueOf(objid.split("_")[2])){
                        case 1:
                            tv_businesstype.setText("个人业务");
                            break;
                        case 2:
                            tv_businesstype.setText("贵宾业务");
                            break;
                    }

                    tv_curnum.setText(objid.split("_")[1]);

                }

            } catch (Exception e) {

            }
        }

    }

}
