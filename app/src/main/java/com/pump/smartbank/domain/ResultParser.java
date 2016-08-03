package com.pump.smartbank.domain;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by xu.nan on 2016/8/3.
 */
public class ResultParser implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setResult(result);
        //返回ResponseEntity对象
        return responseEntity;
    }
}
