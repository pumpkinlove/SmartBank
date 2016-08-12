package com.pump.smartbank.util;

import android.util.Log;

/**
 * Created by xu.nan on 2016/8/11.
 */
public class MathUtil {

    public static double xround(double x, int num){
        Log.e("---------- ", ""+x);
        return Math.round(x * Math.pow(10, num)) / Math.pow(10,num) ;
    }

}
