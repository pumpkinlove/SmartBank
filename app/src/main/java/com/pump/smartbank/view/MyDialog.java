package com.pump.smartbank.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pump.smartbank.R;

public class MyDialog extends Dialog {
	private TextView titleTxv;
	private TextView msgTxv;
	private TextView positiveTxv;
	private TextView negativeTxv;

	public MyDialog(Context context) {
		super(context,R.style.my_dialog);
		setCustomView();
	}

	public MyDialog(Context context,String title, String content) {
		super(context,R.style.my_dialog);
		setCustomView();
		titleTxv.setText(title);
		msgTxv.setText(content);
		showTitle();
	}

	public MyDialog(Context context, String content) {
		super(context,R.style.my_dialog);
		setCustomView();
		msgTxv.setText(content);
	}
	
	private void setCustomView(){
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog, null);
		titleTxv = (TextView) mView.findViewById(R.id.dialog_title);
		msgTxv = (TextView) mView.findViewById(R.id.dialog_content);
		positiveTxv = (TextView) mView.findViewById(R.id.d_positiveButton);
		negativeTxv = (TextView) mView.findViewById(R.id.d_negativeButton);
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

}