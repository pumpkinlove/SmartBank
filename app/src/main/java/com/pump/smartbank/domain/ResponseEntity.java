package com.pump.smartbank.domain;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by xu.nan on 2016/8/3.
 */
@HttpResponse(parser = ResultParser.class)
public class ResponseEntity {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}