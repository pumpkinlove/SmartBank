package com.pump.smartbank.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by xu.nan on 2016/7/27.
 */
public class PictureUtil {

    /**
     * bitmap 转换为 byte数组
     * @param bitmap
     * @return
     */
    public static byte[] convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return appicon;
    }
}
