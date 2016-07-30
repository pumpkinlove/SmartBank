package com.pump.smartbank.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by xu.nan on 2016/7/29.
 */
public class SwipeGallery extends Gallery {

    public SwipeGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int keyCode;
        //这样能够实现每次滑动只滚动一张图片的效果
        if (isScrollingLeft(e1,e2)) {
            keyCode= KeyEvent.KEYCODE_DPAD_LEFT;
        }else{
            keyCode= KeyEvent.KEYCODE_DPAD_RIGHT;
        }
            onKeyDown(keyCode,null);
        return true;
    }
}