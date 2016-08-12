package com.pump.smartbank.emqttd.main;

import com.pump.smartbank.emqttd.callback.PubliserMqttCallbackHandler;
import com.pump.smartbank.emqttd.callback.SubscriberMqttCallbackHandler;
import com.pump.smartbank.emqttd.publisher.MqttPublisher;
import com.pump.smartbank.emqttd.subscriber.MqttSubscriber;

import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MainTest {

    public static void main(String[] args) throws MqttException, InterruptedException {

//        startMqttSubscriber();
//
//        startMqttPublisher();
    }

//    public static void startMqttSubscriber() {
//        new Thread(new Runnable() {
//
//            public void run() {
//                try {
//                    MqttSubscriber subscriber = new MqttSubscriber(MqttConfig.SERVER_URL,
//                        "subscriber_client", new SubscriberMqttCallbackHandler());
//
//                    subscriber.connect();
//                    subscriber.subscribe("topic", 2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }, "mqtt-subscriber-thread").start();
//    }

//    public static void startMqttPublisher() {
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    MqttPublisher publisher = MqttPublisher.getInstance(MqttConfig.SERVER_URL,
//                        "publiser_client", new PubliserMqttCallbackHandler());
//                    publisher.connect();
//
//                    while (true) {
//                        publisher.publish("hello world", "topic");
//                        TimeUnit.SECONDS.sleep(3);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "mqtt-publisher-thread").start();
//    }

}
