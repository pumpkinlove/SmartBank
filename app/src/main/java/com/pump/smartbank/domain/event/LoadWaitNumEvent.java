package com.pump.smartbank.domain.event;

/**
 * Created by xu.nan on 2016/8/29.
 */
public class LoadWaitNumEvent {

    private String queueId;

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public LoadWaitNumEvent(String queueId) {
        this.queueId = queueId;
    }

    public LoadWaitNumEvent() {
    }
}
