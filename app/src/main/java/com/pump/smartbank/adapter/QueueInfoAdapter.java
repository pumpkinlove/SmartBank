package com.pump.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.QueueInfo;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/8/25.
 */
public class QueueInfoAdapter extends RecyclerView.Adapter<QueueInfoAdapter.QueueViewHolder> {

    private List<QueueInfo> queueInfoList;
    private Context context;

    public QueueInfoAdapter(List<QueueInfo> queueInfoList, Context context) {
        this.queueInfoList = queueInfoList;
        this.context = context;
    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QueueViewHolder(LayoutInflater.from(context).inflate(R.layout.item_queue_info, parent, false));
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        holder.tv_title.setText(queueInfoList.get(position).getTitle());
        holder.tv_waitnum.setText(queueInfoList.get(position).getOrganid());
    }

    @Override
    public int getItemCount() {
        return queueInfoList.size();
    }

    static class QueueViewHolder extends RecyclerView.ViewHolder{

        @ViewInject(R.id.tv_queue_wainum)
        private TextView tv_waitnum;
        @ViewInject(R.id.tv_queue_title)
        private TextView tv_title;

        public QueueViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }

    }
}
