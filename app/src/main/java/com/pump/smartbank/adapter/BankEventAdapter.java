package com.pump.smartbank.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.BankEvent;
import com.pump.smartbank.domain.Notice;

import java.util.Collections;
import java.util.List;


/**
 * Created by xu.nan on 2016/7/28.
 */
public class BankEventAdapter extends RecyclerView.Adapter<BankEventAdapter.BankEventViewHolder>{

    private List<BankEvent> bankEventList;
    private Context context;

    public BankEventAdapter(List<BankEvent> bankEventList, Context context) {
        this.bankEventList = bankEventList;
        this.context=context;
    }


    //自定义ViewHolder类
    static class BankEventViewHolder extends RecyclerView.ViewHolder{

        TextView tv_event_name;
        TextView tv_event_content;
        ImageView iv_event_photo;

        public BankEventViewHolder(final View itemView) {
            super(itemView);
            tv_event_name = (TextView) itemView.findViewById(R.id.tv_event_name);
            tv_event_content = (TextView) itemView.findViewById(R.id.tv_event_content);
            iv_event_photo = (ImageView) itemView.findViewById(R.id.iv_event_photo);
        }


    }
    @Override
    public BankEventAdapter.BankEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_bank_event,viewGroup,false);
        BankEventViewHolder bvh = new BankEventViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BankEventAdapter.BankEventViewHolder bankEventViewHolder, int i) {
        bankEventViewHolder.tv_event_name.setText(bankEventList.get(i).getTitle());
        bankEventViewHolder.tv_event_content.setText(bankEventList.get(i).getContent());
        byte[] data = bankEventList.get(i).getPhoto();
//        bankEventViewHolder.iv_event_photo.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));
    }

    @Override
    public int getItemCount() {
        return bankEventList.size();
    }
}