package com.pump.smartbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.emqttd.callback.MqttCallbackHandler;
import com.pump.smartbank.emqttd.subscriber.MqttSubscriber;
import com.pump.smartbank.util.DbUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.xutils.DbManager;
import org.xutils.x;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmqttService extends Service {

    private Config config;
    private ScheduledExecutorService scheduler;
    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;
    private MqttSubscriber subscriber;

    public EmqttService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"绑定消息推送服务",Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"create",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"startcommand",Toast.LENGTH_SHORT).show();
        daoConfig = DbUtil.getDaoConfig();
        dbManager = x.getDb(daoConfig);

        reConnect(null);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "消息推送服务结束", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    private void reConnect(final MqttClient client) {
        Log.e("reConnect","reConnect---------");
        if(scheduler != null){
            Log.e(scheduler.isShutdown()+"","isShutdown -------");
            Log.e(scheduler.isTerminated()+"","isTerminated -----------");
            ;
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e("scheduler run ","进入 run方法");
                if(client == null || !client.isConnected()) {
                    Log.e("reConnect ===== re ","重连 连接开始");
                    connect();
                }
                Log.e("connect","重连  连接 完成");
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void connect(){
        try {
            Log.e("connect","进入connect ------------");
            config = dbManager.findFirst(Config.class);
            subscriber = MqttSubscriber.getInstance(
                    "tcp://" + config.getEmqttIp() + ":" + config.getEmqttPort(),
                    config.getClientId(),
                    new MqttCallbackHandler() {
                    @Override
                        public void connectionLost(Throwable cause) {
                            Log.e("lost","lost =============== l失去连接");
                            reConnect(client);
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.e(topic, message.toString());
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    }
            );
            subscriber.connect();
            Log.e(" connect  connect"," 连接成功 -----");
            if(scheduler != null){
                Log.e(" scheduler  shutdown","shut down 定时任务");
                scheduler.shutdown();
            }
        } catch (Exception e) {
            Log.e("exception", "连接失败  异常"+e.getMessage());
        }
    }

}
