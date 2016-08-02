package com.pump.smartbank.view;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pump.smartbank.R;

public class MyDialog extends Dialog {
	private Context context;
	private TextView titleTxv;
	private TextView msgTxv;
	private TextView positiveTxv;
	private TextView negativeTxv;
	private FrameLayout dFrameLayout;

	public MyDialog(Context context) {
		super(context,R.style.my_dialog);
		this.context = context;
		setCustomView();
	}

	public MyDialog(Context context,String title, String content) {
		super(context,R.style.my_dialog);
		this.context = context;
		setCustomView();
		titleTxv.setText(title);
		msgTxv.setText(content);
		showTitle();
	}

	public MyDialog(Context context, String content) {
		super(context,R.style.my_dialog);
		this.context = context;
		setCustomView();
		msgTxv.setText(content);
	}
	
	private void setCustomView(){
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog, null);
		titleTxv = (TextView) mView.findViewById(R.id.dialog_title);
		msgTxv = (TextView) mView.findViewById(R.id.dialog_content);
		positiveTxv = (TextView) mView.findViewById(R.id.d_positiveButton);
		negativeTxv = (TextView) mView.findViewById(R.id.d_negativeButton);
		dFrameLayout = (FrameLayout) mView.findViewById(R.id.d_frame_layout);
		super.setContentView(mView);
	}
	
	@Override
	public void setContentView(View view){
	}

	@Override
	public void setTitle(CharSequence title) {
		titleTxv.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getContext().getString(titleId));
	}
	
	public void setMessage(CharSequence msg){
		msgTxv.setText(msg);
	}

	public void setPositiveText(CharSequence text){
		positiveTxv.setText(text);
	}

	public void setNegativeText(CharSequence text){
		negativeTxv.setText(text);
	}
	
	public void setOnPositiveListener(View.OnClickListener listener){
		positiveTxv.setOnClickListener(listener);
	}

	public void setOnNegativeListener(View.OnClickListener listener){
		negativeTxv.setOnClickListener(listener);
	}
	
	public void myInit(String title, String message, String positiveText, String negativeText, 
			View.OnClickListener positiveListener, View.OnClickListener nagtiveListener){
		this.setTitle(title);
		this.setMessage(message);
		this.setPositiveText(positiveText);
		this.setNegativeText(negativeText);
		this.setOnPositiveListener(positiveListener);
		this.setOnNegativeListener(nagtiveListener);
	}

	public void showTitle(){
		LinearLayout ll =(LinearLayout) findViewById(R.id.d_ll_title);
		ll.setVisibility(View.VISIBLE);
	}

	public void hideContent(){
		msgTxv.setVisibility(View.GONE);
	}

	public void hideBottom(){
		LinearLayout ll =(LinearLayout) findViewById(R.id.d_ll_bottom);
		ll.setVisibility(View.GONE);
	}

	public void showFrameLayout(){
		dFrameLayout.setVisibility(View.VISIBLE);
	}

}