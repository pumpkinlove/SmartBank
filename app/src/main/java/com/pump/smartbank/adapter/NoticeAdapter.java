package com.pump.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.Notice;
import com.pump.smartbank.util.DateUtil;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;


/**
 * Created by xu.nan on 2016/7/28.
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{

    private List<Notice> noticeList;
    private Context context;

    public NoticeAdapter(List<Notice> noticeList, Context context) {
        this.noticeList = noticeList;
        this.context=context;
    }


    //自定义ViewHolder类
    static class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView tv_position;
        TextView tv_content;
        TextView tv_time;

        public NoticeViewHolder(final View itemView) {
            super(itemView);
            tv_position = (TextView) itemView.findViewById(R.id.tv_notice_position);
            tv_content = (TextView) itemView.findViewById(R.id.tv_notice_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_notice_time);
        }


    }
    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_notice,viewGroup,false);
        NoticeViewHolder nvh=new NoticeViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.NoticeViewHolder personViewHolder, int i) {
        Collections.reverse(noticeList);
        personViewHolder.tv_position.setText(noticeList.get(i).getPosition());
        personViewHolder.tv_content.setText(noticeList.get(i).getContent());
        personViewHolder.tv_time.setText(noticeList.get(i).getTime());
        Collections.reverse(noticeList);
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }
}