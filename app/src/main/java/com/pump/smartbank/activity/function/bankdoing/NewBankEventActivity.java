package com.pump.smartbank.activity.function.bankdoing;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.comm.BankDoingComm;
import com.pump.smartbank.comm.BaseComm;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.net.Socket;
import java.net.URI;
import java.util.Date;
import java.util.Random;

@ContentView(R.layout.activity_new_bank_event)
public class NewBankEventActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;
    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    private Bitmap bitmap;
    @ViewInject(R.id.iv_new_event_photo)
    private ImageView iv_photo;


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
        tv_leftContent.setVisibility(View.VISIBLE);
        tv_middleContent.setText("新建活动");
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

    @Event(value={R.id.tv_leftContent, R.id.btn_take_photo, R.id.btn_new_event_finish},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
            case R.id.btn_take_photo:
                startCamera();
                break;
            case R.id.btn_new_event_finish:

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

    public void startCamera() {
//        Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null);
//        this.sendBroadcast(i);
        long dateTaken = System.currentTimeMillis();
        String fileName = Environment.getExternalStorageDirectory().getPath()+"/"+ new Random().nextInt(100000)+".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("data", fileName);
        values.put(MediaStore.Images.Media.PICASA_ID, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION, fileName);
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName);
        Uri photoUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(inttPhoto, 1);
        //调用系统的拍照功能
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra("autofocus", true);//进行自动对焦操作
//        intent.putExtra("fullScreen", false);//设置全屏
//        intent.putExtra("showActionIcons", false);
//        File tempFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+new Date()+".jpg");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, URI.);
//        startActivityForResult(intent, 1);
    }
}
