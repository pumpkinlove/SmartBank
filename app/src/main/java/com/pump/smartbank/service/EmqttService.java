package com.pump.smartbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pump.smartbank.R;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.Notice;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.WatchStatus;
import com.pump.smartbank.util.DateUtil;
import com.pump.smartbank.util.DbUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmqttService extends Service {

    private Config config;
    private Handler handler;
    private MqttClient client;
    private String myTopic = "topic1";
    private MqttConnectOptions options;
    private ScheduledExecutorService scheduler;
    private DbManager.DaoConfig daoConfig;
    private DbManager dbManager;

    public EmqttService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Toast.makeText(this,"绑定消息推送服务",Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"create",Toast.LENGTH_LONG).show();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1) {
                    Toast.makeText(EmqttService.this, (String) msg.obj,
                            Toast.LENGTH_SHORT).show();
                    System.out.println("-----------------------------");
                    int msgType = Integer.valueOf(((String) msg.obj).substring(0,1));
                    Intent intent = new Intent();
                    switch (msgType){
                        case 1:
                            intent.putExtra("informType", 1);
                            intent.putExtra("notice", new Notice("差评",DateUtil.toMonthDay(new Date()) ,DateUtil.toHourMinString(new Date()),"窗口1", 1));
                            intent.setAction("android.intent.action.test");//action与接收器相同
                            sendBroadcast(intent);
                            break;
                        case 2:
                            intent.putExtra("informType", 2);
                            intent.putExtra("watchStatus", new WatchStatus("1","张三", 11,2,1));
                            intent.setAction("android.intent.action.test");//action与接收器相同
                            sendBroadcast(intent);
                            break;
                        case 3:
                            intent.putExtra("informType", 3);
                            downLoadCustomer(intent,(String)((String) msg.obj).split("3")[1]);
                            break;
                        case 4:
                            intent.putExtra("informType", 4);
                            intent.putExtra("testReMessage", (String)msg.obj);
                            intent.setAction("android.intent.action.test");
                            sendBroadcast(intent);
                            break;
                    }
                } else if(msg.what == 2) {
                    Toast.makeText(EmqttService.this, "连接成功", Toast.LENGTH_SHORT).show();
                    try {
                        client.subscribe(myTopic, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(msg.what == 3) {
                    Toast.makeText(EmqttService.this, "连接失败，系统正在重连", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"startcommand",Toast.LENGTH_LONG).show();

        daoConfig = DbUtil.getDaoConfig();
        dbManager = x.getDb(daoConfig);
        try {
            config = dbManager.findFirst(Config.class);
            if(config != null){
                if(client == null ){
                    initEmqtt();
                    startReconnect();
                }else if(!client.isConnected()){
                    initEmqtt();
                    startReconnect();
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "消息推送服务结束", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    private void initEmqtt() {
        try {
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient("tcp://"+config.getEmqttIp()+":"+config.getEmqttPort(), config.getClientId(), new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //设置连接的用户名
            options.setUserName(config.getEmqttUsername());
            //设置连接的密码
            options.setPassword(config.getEmqttPassword().toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(60);
            options.setAutomaticReconnect(true);
            //设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    Toast.makeText(EmqttService.this, "失去连接", Toast.LENGTH_SHORT).show();
                    System.out.println("connectionLost----------");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }

                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    System.out.println("messageArrived----------");
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = message.toString();
                    handler.sendMessage(msg);
                }
            });
//          connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if(!client.isConnected()) {
                    try {
                        config = dbManager.findFirst(Config.class);
                        if(config != null){
                            initEmqtt();
                            connect();
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void connect() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    client.connect(options);
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void downLoadCustomer(final Intent intent, String customerName){
        Toast.makeText(this,"faf",Toast.LENGTH_LONG).show();
        try {
            Config config = dbManager.findFirst(Config.class);

            RequestParams params = new RequestParams("http://"+config.getSocketIp()+":"+config.getSocketPort() + "/CIIPS_A/customer/select.action");

            params.addParameter("customname",customerName);

            x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {

                @Override
                public void onSuccess(ResponseEntity response) {
                    String reJson = response.getResult();
                    Gson g = new Gson();
                    intent.putExtra("customer", g.fromJson(reJson,Customer.class));
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

}
