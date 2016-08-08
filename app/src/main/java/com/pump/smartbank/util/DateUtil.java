package com.pump.smartbank.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 计算周岁
     * @param dateStr   “19990101”
     * @return
     */
    public static int getAge(String dateStr){
        int age = 0;
        Date now = new Date();
        int yNow = getYear(now);
        int y = Integer.valueOf(dateStr.substring(0,4));
        age = yNow - y;

        int mNow = getMonth(now);
        int m = Integer.valueOf(dateStr.substring(4,6));
        int dNow = getDay(now);
        int d = Integer.valueOf(dateStr.substring(6,8));
        if(m > mNow){
            age --;
        }else if(d > dNow){
            age --;
        }
        return age;
    }

    private static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    private static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    private static int getDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

}
