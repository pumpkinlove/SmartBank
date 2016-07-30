package com.pump.smartbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/7/29.
 * 窗口业务监控
 */
@Table(name="watchStatus")
public class WatchStatus implements Serializable {
    @Column(name = "id",isId = true,autoGen = true)
    private int id;
    @Column(name = "position")
    private String position;                //窗口号
    @Column(name = "workerName")
    private String workerName;              //营业员姓名
    @Column(name = "good")
    private int good;                       //好评数量
    @Column(name = "bad")
    private int bad;                        //差评数量
    @Column(name = "status")
    private int status;                     //状态    1-正在办理业务    2-空闲     3-关闭

    public WatchStatus() {
    }

    public WatchStatus(String position, String workerName, int good, int bad, int status) {
        this.position = position;
        this.workerName = workerName;
        this.good = good;
        this.bad = bad;
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
