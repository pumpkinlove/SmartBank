package com.pump.smartbank.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.ParameterizedType;
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
//        Gson g = new Gson();
//        ResponseEntity<WaitNum> re = ResponseEntity.fromJson(result,
//                WaitNum.class);
        return ResponseEntity.fromJson(result,
                WaitNum.class);

    }

}
