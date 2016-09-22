package com.pump.smartbank.activity.function.bankdoing;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.comm.BankDoingComm;
import com.pump.smartbank.comm.BaseComm;
import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.domain.ResponseEntity;
import com.pump.smartbank.domain.event.LoadBankDoingEvent;
import com.pump.smartbank.util.DbUtil;
import com.pump.smartbank.util.PictureUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.body.RequestBody;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
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

    @ViewInject(R.id.et_bank_event_title)
    private EditText et_title;
    @ViewInject(R.id.et_bank_event_content)
    private EditText et_content;

    private BankEvent bankEvent;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    @Override
    protected void initData() {
        bankEvent = new BankEvent();
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_leftContent.setVisibility(View.VISIBLE);
        tv_middleContent.setText("新建活动");
        progressDialog.setMessage("正在上传...");
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
                upLoadPhoto();
                break;
        }
    }

    private void upLoadPhoto(){
        if(et_title.length() < 1 || et_content.length() < 1){
            return;
        }
        progressDialog.show();
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        DbManager dbManager = x.getDb(daoConfig);
        try {
            Config config = dbManager.findFirst(Config.class);
            RequestParams params = new RequestParams("http://"+config.getHttpIp()+":"+config.getHttpPort() + "/CIIPS_A/bankdoing/upload.action");
            params.setCharset("utf-8");
            bankEvent.setTitle(et_title.getText().toString());
            bankEvent.setContent(et_content.getText().toString());
            bankEvent.setBankName("测试");
            iv_photo.setDrawingCacheEnabled(true);
            bankEvent.setPhoto(PictureUtil.convertIconToString(iv_photo.getDrawingCache()));
            Gson g = new Gson();
            params.addParameter("bankEvent", g.toJson(bankEvent));
            x.http().post(params, new Callback.CommonCallback<String>() {

                @Override
                public void onSuccess(String response) {
                    Toast.makeText(x.app(),"上传成功",Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(x.app(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(Callback.CancelledException cex) {
                    progressDialog.dismiss();
                }
                @Override
                public void onFinished() {
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startCamera() {
//        Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null);
//        this.sendBroadcast(i);
        long dateTaken = System.currentTimeMillis();
        String fileName = Environment.getExternalStorageDirectory().getPath()+"/"+ dateTaken+".jpg";
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
