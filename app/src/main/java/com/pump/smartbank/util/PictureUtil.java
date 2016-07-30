package com.pump.smartbank.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

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
