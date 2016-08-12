package com.pump.smartbank.emqttd.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class MqttCallbackHandler implements MqttCallback {

    protected MqttClient client;

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public abstract void connectionLost(Throwable cause);

    public abstract void messageArrived(String topic, MqttMessage message) throws Exception;

    public abstract void deliveryComplete(IMqttDeliveryToken token);
}
