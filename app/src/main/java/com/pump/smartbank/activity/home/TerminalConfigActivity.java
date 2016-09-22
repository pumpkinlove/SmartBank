package com.pump.smartbank.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.adapter.TerminalAdapter;
import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.Terminal;
import com.pump.smartbank.domain.TerminalInfo;
import com.pump.smartbank.service.EmqttService;
import com.pump.smartbank.util.CommonUtil;
import com.pump.smartbank.util.DbUtil;
import com.pump.smartbank.util.ServiceUtil;
import com.pump.smartbank.util.XUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_terminal_config)
public class TerminalConfigActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.rv_terminal)
    private RecyclerView rv_terminal;

    private TerminalAdapter terminalAdapter;
    private List<Terminal> terminalList;
    private List<TerminalInfo> tiList;

    private Config config;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        loadTerminalInfo();
    }

    @Override
    protected void initData() {
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        dbManager = x.getDb(daoConfig);
        tiList = new ArrayList<>();
        try {
            config = dbManager.findFirst(Config.class);
            terminalList = dbManager.findAll(Terminal.class);
            if (terminalList == null) {
                terminalList = new ArrayList<>();
            }
            terminalAdapter = new TerminalAdapter(terminalList, this);

        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText("终端管理");
        tv_leftContent.setVisibility(View.VISIBLE);
        rv_terminal.setHasFixedSize(true);
        rv_terminal.setLayoutManager(new LinearLayoutManager(this));
        rv_terminal.setAdapter(terminalAdapter);
    }

    @Event(value={R.id.tv_leftContent},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        for(int i=0; i<terminalList.size(); i++){
            try {
                dbManager.update(terminalList.get(i));
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        if(ServiceUtil.isServiceWork(this, "com.pump.smartbank.service.EmqttService")){
            stopService(new Intent(this, EmqttService.class));
        }
        startService( new Intent(this, EmqttService.class));

        super.finish();
    }

    private void loadTerminalInfo(){
        Map<String, Object> params = new HashMap<>();
        params.put("deptno", config.getClientId());
        String url = "http://"+config.getHttpIp()+":"+config.getHttpPort()+"/CIIPS_A/terminal/selectByDeptno.action";
        XUtil.Post(url, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson g = new Gson();
//                try {
//                    result = URLDecoder.decode(result, "utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(result);
                JsonArray jsonArray;
                if (jsonElement.isJsonArray()) {
                    jsonArray = jsonElement.getAsJsonArray();
                    Iterator it = jsonArray.iterator();
                    while(it.hasNext()){
                        JsonElement e = (JsonElement) it.next();
                        TerminalInfo tii = g.fromJson(e, TerminalInfo.class);
                        tiList.add(tii);
                    }
                    terminalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                checkTerminal();
            }
        });

    }

    private void checkTerminal(){
        for(int i=0; i<terminalList.size(); i++){
            boolean flag = false;
            for(int j=0; j<tiList.size(); j++){
                if(terminalList.get(i).getTerminalno().equals(tiList.get(j).getTerminalno())){
                    flag = true;
                }
            }
            if(!flag){
                terminalList.remove(i);
                try {
                    dbManager.delete(terminalList.get(i));
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }

        for(int i=0; i<tiList.size(); i++){
            boolean flag = false;
            for(int j=0; j<terminalList.size(); j++){
                if(terminalList.get(j).getTerminalno().equals(tiList.get(i).getTerminalno())){
                    flag = true;
                }
            }
            if(!flag){
                Terminal terminal = new Terminal();
                terminal.setTerminalno(tiList.get(i).getTerminalno());
                if("1".equals(tiList.get(i).getTerminaltype())){
                    terminal.setTerminaltype("无");
                }else if("2".equals(tiList.get(i).getTerminaltype())) {
                    terminal.setTerminaltype("证鉴通");
                }else if("3".equals(tiList.get(i).getTerminaltype())) {
                    terminal.setTerminaltype("一体机");
                }else if("4".equals(tiList.get(i).getTerminaltype())) {
                    terminal.setTerminaltype("柜内助手");
                }
                terminal.setSubscribe(false);
                terminalList.add(terminal);
                try {
                    dbManager.save(terminal);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }

        terminalAdapter.notifyDataSetChanged();
    }

}
