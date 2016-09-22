package com.pump.smartbank.domain.event;

/**
 * Created by xu.nan on 2016/9/1.
 */
public class InformComeEvent {

    public InformComeEvent() {
    }

    public InformComeEvent(String informJson) {
        this.informJson = informJson;
    }

    private String informJson;

    public String getInformJson() {
        return informJson;
    }

    public void setInformJson(String informJson) {
        this.informJson = informJson;
    }
}
