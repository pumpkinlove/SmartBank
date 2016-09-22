package com.pump.smartbank.util;

import com.pump.smartbank.domain.Customer;

import java.util.Date;

/**
 * Created by xu.nan on 2016/8/16.
 */
public class EmqttdUtil {

    /**
     * 从emqttd 返回message中解析出cutomer
     * @param str
     * @return
     */
    public static Customer parseCustormer(String str){
        Customer customer = new Customer();
        String[] pieces = str.split("%");
        if(pieces == null || pieces.length < 1){
            return null;
        }
        if ( !"1".equals(pieces[0]) ){
            return null;
        }
        customer.setCustomname(pieces[1]);
        customer.setComeDate(DateUtil.toMonthDay(new Date()));
        customer.setComeTime(DateUtil.toHourMinString(new Date()));
        return null;
    }
}
