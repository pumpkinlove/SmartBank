package com.pump.smartbank.emqttd.publisher;

import com.pump.smartbank.emqttd.callback.MqttCallbackHandler;
import com.pump.smartbank.emqttd.client.AbstractMqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublisher extends AbstractMqttClient {
	
	private static MqttPublisher publisher;
	
	public static synchronized MqttPublisher getInstance(String serverURI, String clientId, MqttCallbackHandler callbackHandler){
		if(publisher == null){
			publisher = new MqttPublisher(serverURI, clientId, callbackHandler);
		}
		return publisher;
	}
	
	
    private MqttPublisher(String serverURI, String clientId) {
        super(serverURI, clientId);
    }

    private MqttPublisher(String serverURI, String clientId, MqttCallbackHandler callbackHandler) {
        super(serverURI, clientId, callbackHandler);
    }

    private MqttPublisher(String serverURI, String clientId, MqttCallbackHandler callbackHandler,
                         MqttClientPersistence persistence) {
        super(serverURI, clientId, callbackHandler, persistence);
    }

    public void connect() {
        try {
            client = new MqttClient(this.serverURI, this.clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);

            if (null != callbackHandler) {
                callbackHandler.setClient(client);
                client.setCallback(callbackHandler);
            }

            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String msg, String topic) {
        try {
            MqttMessage message = new MqttMessage(msg.getBytes("utf-8"));

            message.setQos(2);
            message.setRetained(false);

            MqttDeliveryToken token = client.getTopic(topic).publish(message);
            token.waitForCompletion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
