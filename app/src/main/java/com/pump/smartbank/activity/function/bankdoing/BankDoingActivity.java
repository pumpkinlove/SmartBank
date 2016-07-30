package com.pump.smartbank.activity.function.bankdoing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.comm.BankDoingComm;
import com.pump.smartbank.comm.BaseComm;
import com.pump.smartbank.comm.GetTimeComm;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.Socket;

@ContentView(R.layout.activity_bank_doing)
public class BankDoingActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;
    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;
    @ViewInject(R.id.iv_photo)
    private ImageView iv_photo;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        initCamera();
    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initView(){
        x.view().inject(this);
        tv_middleContent.setText("网点活动跟踪");
        tv_leftContent.setVisibility(View.VISIBLE);
    }

    private void initCamera(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){

                bitmap = (Bitmap) data.getExtras().get("data");
                iv_photo.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Event(value={R.id.tv_leftContent,R.id.btn_take_photo,R.id.btn_upload_photo},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.btn_take_photo:
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
                break;
            case R.id.btn_upload_photo:
                upLoadPhoto();
                break;
        }
    }

    private void upLoadPhoto(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = BaseComm.connect("192.168.2.106", 7000, 10000, new StringBuilder());
                BankDoingComm comm = new BankDoingComm(socket, bitmap);
                int result = comm.executeComm();
                if (result != 0) {
                    System.out.print(comm.message);
                    BaseComm.close(socket);
                }
                BaseComm.close(socket);
            }
        }).start();
    }
}
