package com.pump.smartbank.activity.function.ciips;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.view.MyDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_ciips)
public class CiipsActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.wv_ciips)
    private WebView wv_ciips;

    private MyDialog backDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();
        initWebView();
    }

    @Override
    protected void initData() {
        backDialog = new MyDialog(this,"确定退出填单吗？");
        backDialog.setOnNegativeListener(this);
        backDialog.setOnPositiveListener(this);
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText("预填单系统");
        tv_leftContent.setVisibility(View.VISIBLE);
        //WebView加载web资源
        wv_ciips.loadUrl("http://www.zzjrfw.cn/CIIPS_C/app/mobile/category.jsp");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv_ciips.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initWebView(){
        WebSettings s = wv_ciips.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setDomStorageEnabled(true);
        wv_ciips.requestFocus();
    }

    @Event(value={R.id.tv_leftContent,R.id.d_negativeButton,R.id.d_positiveButton},type=View.OnClickListener.class)
    private void onClicked(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                backDialog.show();
                break;
            case R.id.d_positiveButton:
                finish();
                break;
            case R.id.d_negativeButton:
                backDialog.dismiss();
                break;
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                backDialog.show();
                break;
            case R.id.d_positiveButton:
                backDialog.dismiss();
                finish();
                break;
            case R.id.d_negativeButton:
                backDialog.dismiss();
                break;
        }
    }
}
