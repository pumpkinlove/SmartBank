package com.pump.smartbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.pump.smartbank.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

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

    }
}
