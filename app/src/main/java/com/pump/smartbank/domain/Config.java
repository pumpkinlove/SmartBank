package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/7/27.
 * 系统设置
 */
@Table(name="config", onCreated = "")
public class Config implements Serializable {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "emqttIp")
    private String emqttIp;
    @Column(name = "emqttPort")
    private String emqttPort;
    @Column(name = "httpIp")
    private String httpIp;
    @Column(name = "httpPort")
    private String httpPort;
    @Column(name = "clientId")
    private String clientId;        //终端号   pad唯一标识符
    @Column(name = "emqttUsername")
    private String emqttUsername;
    @Column(name = "emqttPassword")
    private String emqttPassword;

    public Config() {
    }

    public Config(int id, String emqttIp, String emqttPort, String httpIp, String httpPort, String clientId, String emqttUsername, String emqttPassword) {
        this.id = id;
        this.emqttIp = emqttIp;
        this.emqttPort = emqttPort;
        this.httpIp = httpIp;
        this.httpPort = httpPort;
        this.clientId = clientId;
        this.emqttUsername = emqttUsername;
        this.emqttPassword = emqttPassword;
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

    public String getEmqttUsername() {
        return emqttUsername;
    }

    public void setEmqttUsername(String emqttUsername) {
        this.emqttUsername = emqttUsername;
    }

    public String getEmqttPassword() {
        return emqttPassword;
    }

    public void setEmqttPassword(String emqttPassword) {
        this.emqttPassword = emqttPassword;
    }

    public String getHttpIp() {
        return httpIp;
    }

    public void setHttpIp(String httpIp) {
        this.httpIp = httpIp;
    }

    public String getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", emqttIp='" + emqttIp + '\'' +
                ", emqttPort='" + emqttPort + '\'' +
                ", httpIp='" + httpIp + '\'' +
                ", httpPort='" + httpPort + '\'' +
                ", clientId='" + clientId + '\'' +
                ", emqttUsername='" + emqttUsername + '\'' +
                ", emqttPassword='" + emqttPassword + '\'' +
                '}';
    }
}
