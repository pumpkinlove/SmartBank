package com.pump.smartbank.emqttd.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class PubliserMqttCallbackHandler extends MqttCallbackHandler {

    @Override
    public void connectionLost(Throwable cause) {
    	try {
    		MqttClient c = this.getClient();
			if(c == null){
				
			}else if(!c.isConnected()){
				c.connect();
			}
		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("publiser message:" + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
