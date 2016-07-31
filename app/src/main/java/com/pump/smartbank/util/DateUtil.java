package com.pump.smartbank.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xu.nan on 2016/7/28.
 */
public class DateUtil {

    public static String toHourMinString(Date date){
        SimpleDateFormat myFmt=new SimpleDateFormat("HH:mm");
        return myFmt.format(date);
    }

    public static String toMonthDay(Date date){
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(date);
    }

}
