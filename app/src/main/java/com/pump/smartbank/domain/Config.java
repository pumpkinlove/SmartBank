package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/7/27.
 * 系统设置
 */
@Table(name="config")
public class Config implements Serializable {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "emqttIp")
    private String emqttIp;
    @Column(name = "emqttPort")
    private String emqttPort;
    @Column(name = "socketIp")
    private String socketIp;
    @Column(name = "socketPort")
    private String socketPort;
    @Column(name = "clientId")
    private String clientId;        //终端号

    public Config() {
    }

    public Config(String emqttIp, String emqttPort, String socketIp, String socketPort, String clientId) {
        this.emqttIp = emqttIp;
        this.emqttPort = emqttPort;
        this.socketIp = socketIp;
        this.socketPort = socketPort;
        this.clientId = clientId;
    }

    public String getEmqttIp() {
        return emqttIp;
    }

    public void setEmqttIp(String emqttIp) {
        this.emqttIp = emqttIp;
    }

    public String getEmqttPort() {
        return emqttPort;
    }

    public void setEmqttPort(String emqttPort) {
        this.emqttPort = emqttPort;
    }

    public String getSocketIp() {
        return socketIp;
    }

    public void setSocketIp(String socketIp) {
        this.socketIp = socketIp;
    }

    public String getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(String socketPort) {
        this.socketPort = socketPort;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +  '\'' +
                ", emqttIp='" + emqttIp + '\'' +
                ", emqttPort='" + emqttPort + '\'' +
                ", socketIp='" + socketIp + '\'' +
                ", socketPort='" + socketPort + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
