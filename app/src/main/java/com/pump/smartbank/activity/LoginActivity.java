package com.pump.smartbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import com.pump.smartbank.R;
import com.pump.smartbank.domain.ResponseEntity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        x.view().inject(this);
    }

    @Event(value={R.id.btn_login},type=View.OnClickListener.class)
    private void onClick(final View view){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

//        RequestParams params = new RequestParams("http://192.168.5.215:8080/yygl/test/upload");
//        String fileName =  Environment.getExternalStorageDirectory().getPath()+"/86810.jpg";
//        params.addBodyParameter("upload", new File(fileName));
//        x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {
//
//            @Override
//            public void onSuccess(ResponseEntity result) {
//                Snackbar.make(view, "上传成功", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Snackbar.make(view, "上传失败", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//            @Override
//            public void onCancelled(Callback.CancelledException cex) {
//            }
//            @Override
//            public void onFinished() {
//            }
//        });
    }
}
