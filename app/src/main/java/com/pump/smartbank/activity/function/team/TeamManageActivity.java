package com.pump.smartbank.activity.function.team;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_team_manage)
public class TeamManageActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

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
        tv_middleContent.setText("队列管理");
        tv_leftContent.setVisibility(View.VISIBLE);
    }

    @Event(value={R.id.tv_leftContent },type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
        }
    }
}
