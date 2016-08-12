package com.pump.smartbank.emqttd.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscriberMqttCallbackHandler extends MqttCallbackHandler {

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        System.out.println("subscriber message:" + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
