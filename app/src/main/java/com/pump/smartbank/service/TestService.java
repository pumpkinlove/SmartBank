package com.pump.smartbank.service;

import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.response.BankEventResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public interface TestService {

    @GET("bankdoing/findall.action")
    Call<List<BankEventResponse>> testHttp();

}
