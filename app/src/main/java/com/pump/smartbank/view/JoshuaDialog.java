package com.pump.smartbank.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pump.smartbank.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/8/25.
 */
public class JoshuaDialog extends DialogFragment {

    @ViewInject(R.id.tv_dialog_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.tv_dialog_cancel)
    private TextView tv_cancel;
    @ViewInject(R.id.tv_dialog_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_dialog_content)
    private TextView tv_content;

    @ViewInject(R.id.ll_dialog_middle)
    private LinearLayout ll_middle;

    @ViewInject(R.id.ll_dialog_bottom)
    private LinearLayout ll_bottom;

    @ViewInject(R.id.iv_dialog)
    private ImageView iv_dialog;

    private View.OnClickListener confirmListener;
    private View.OnClickListener cancelListener;

    private String title;
    private String content;
    private String confirmMessage;
    private String cancelMessage;
    private boolean errorFlag = false;

    @Override
    public void onStart() {
        Log.e("------------","onStart");
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("------------","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("------------","onCreateView");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.layout_joshua_dialog, container);
        x.view().inject(this, view);

        initData();
        initView();

        return view;
    }

    private void initData(){

    }

    private void initView(){

        if(errorFlag){
            tv_title.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
            iv_dialog.setVisibility(View.VISIBLE);
        }

        if(title != null){
            tv_title.setText(title);
        }else{
            tv_title.setVisibility(View.GONE);
        }
        tv_content.setText(content);
        if(confirmMessage != null){
            tv_confirm.setText(confirmMessage);
        }
        if(cancelMessage != null){
            tv_cancel.setText(cancelMessage);
        }
        tv_confirm.setOnClickListener(confirmListener);
        tv_cancel.setOnClickListener(cancelListener);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.e("------------","onDismiss");
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        Log.e("------------","onDestroy");
        super.onDestroy();
    }

    public void setErrorFlag(boolean errorFlag){
        this.errorFlag = errorFlag;
    }

    public String getCancelMessage() {
        return cancelMessage;
    }

    public void setCancelMessage(String cancelMessage) {
        this.cancelMessage = cancelMessage;
    }

    public String getConfirmMessage() {
        return confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public View.OnClickListener getConfirmListener() {
        return confirmListener;
    }

    public void setConfirmListener(View.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public TextView getTv_confirm() {
        return tv_confirm;
    }

    public void setTv_confirm(TextView tv_confirm) {
        this.tv_confirm = tv_confirm;
    }

    public TextView getTv_cancel() {
        return tv_cancel;
    }

    public void setTv_cancel(TextView tv_cancel) {
        this.tv_cancel = tv_cancel;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public TextView getTv_content() {
        return tv_content;
    }

    public void setTv_content(TextView tv_content) {
        this.tv_content = tv_content;
    }

    public LinearLayout getLl_middle() {
        return ll_middle;
    }

    public void setLl_middle(LinearLayout ll_middle) {
        this.ll_middle = ll_middle;
    }

    public LinearLayout getLl_bottom() {
        return ll_bottom;
    }

    public void setLl_bottom(LinearLayout ll_bottom) {
        this.ll_bottom = ll_bottom;
    }

    public ImageView getIv_dialog() {
        return iv_dialog;
    }

    public void setIv_dialog(ImageView iv_dialog) {
        this.iv_dialog = iv_dialog;
    }
}
