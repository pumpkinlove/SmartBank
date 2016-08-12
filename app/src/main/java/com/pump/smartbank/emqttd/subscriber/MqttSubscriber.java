package com.pump.smartbank.emqttd.subscriber;

import com.pump.smartbank.emqttd.callback.MqttCallbackHandler;
import com.pump.smartbank.emqttd.client.AbstractMqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttSubscriber extends AbstractMqttClient {

    private static MqttSubscriber mqttSubscriber;

    public MqttSubscriber(String serverURI, String clientId) {
        super(serverURI, clientId);
    }

    public MqttSubscriber(String serverURI, String clientId, MqttCallbackHandler callbackHandler) {
        super(serverURI, clientId, callbackHandler);
    }

    public MqttSubscriber(String serverURI, String clientId, MqttCallbackHandler callbackHandler,
                          MqttClientPersistence persistence) {
        super(serverURI, clientId, callbackHandler, persistence);

    }

    public static synchronized MqttSubscriber getInstance(String serverURI, String clientId, MqttCallbackHandler callbackHandler){
        if(mqttSubscriber == null){
            mqttSubscriber = new MqttSubscriber(serverURI, clientId, callbackHandler);
        }else{
            mqttSubscriber.disconnect();
        }

        return mqttSubscriber;
    }

    public void connect() throws MqttException {

        client = new MqttClient(serverURI, clientId, persistence);
        MqttConnectOptions options = new MqttConnectOptions();

        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setUserName("admin");
        options.setPassword("Pumpkin13.".toCharArray());
        options.setConnectionTimeout(100);
        options.setKeepAliveInterval(60);

        if (callbackHandler != null) {
            callbackHandler.setClient(client);
            client.setCallback(callbackHandler);
        }

        client.connect(options);
    }

    public void subscribe(String[] topics, int[] qos) throws MqttException {
        client.subscribe(topics, qos);
    }

    public void subscribe(String topic, int qos) throws MqttException {
        client.subscribe(topic, qos);
    }

}
