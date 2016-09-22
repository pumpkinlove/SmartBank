package com.pump.smartbank.domain;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/8/29.
 */
public class WaitNum implements Serializable{

    private String organid;
    private String queueid;
    private String waitnum;

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getQueueid() {
        return queueid;
    }

    public void setQueueid(String queueid) {
        this.queueid = queueid;
    }

    public String getWaitnum() {
        return waitnum;
    }

    public void setWaitnum(String waitnum) {
        this.waitnum = waitnum;
    }
}
