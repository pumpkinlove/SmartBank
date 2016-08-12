package com.pump.smartbank.emqttd.client;

import com.pump.smartbank.emqttd.callback.MqttCallbackHandler;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public abstract class AbstractMqttClient {

    protected MqttClient            client = null;

    protected String                serverURI;

    protected String                clientId;

    protected MqttClientPersistence persistence;

    protected MqttCallbackHandler callbackHandler;

    public AbstractMqttClient(String serverURI, String clientId) {
        this(serverURI, clientId, null);
    }

    public AbstractMqttClient(String serverURI, String clientId,
                              MqttCallbackHandler callbackHandler) {
        this(serverURI, clientId, callbackHandler, new MemoryPersistence());
    }

    public AbstractMqttClient(String serverURI, String clientId,
                              MqttCallbackHandler callbackHandler,
                              MqttClientPersistence persistence) {
        this.serverURI = serverURI;
        this.clientId = clientId;
        this.callbackHandler = callbackHandler;
        if (null == persistence) {
            persistence = new MemoryPersistence();
        }
        this.persistence = persistence;

    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MqttCallbackHandler getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(MqttCallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public MqttClientPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(MqttClientPersistence persistence) {
        this.persistence = persistence;
    }

    public void disconnect() {
        try {
            if (null != client) {
                client.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
