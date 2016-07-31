package com.pump.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.Notice;
import com.pump.smartbank.domain.WatchStatus;

import java.util.Collections;
import java.util.List;


/**
 * Created by xu.nan on 2016/7/28.
 */
public class WatchStatusAdapter extends RecyclerView.Adapter<WatchStatusAdapter.WatchStatusViewHolder>{

    private List<WatchStatus> watchStatusList;
    private Context context;

    public WatchStatusAdapter(List<WatchStatus> watchStatusList, Context context) {
        this.watchStatusList = watchStatusList;
        this.context=context;
    }


    //自定义ViewHolder类
    static class WatchStatusViewHolder extends RecyclerView.ViewHolder{

        TextView tv_watch_position;
        TextView tv_watch_workername;
        TextView tv_watch_good;
        TextView tv_watch_bad;
        TextView tv_watch_status;

        public WatchStatusViewHolder(final View itemView) {
            super(itemView);
            tv_watch_position = (TextView) itemView.findViewById(R.id.tv_watch_position);
            tv_watch_workername = (TextView) itemView.findViewById(R.id.tv_watch_workername);
            tv_watch_good = (TextView) itemView.findViewById(R.id.tv_watch_good);
            tv_watch_bad = (TextView) itemView.findViewById(R.id.tv_watch_bad);
            tv_watch_status = (TextView) itemView.findViewById(R.id.tv_watch_status);
        }

    }
    @Override
    public WatchStatusAdapter.WatchStatusViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_watch_status,viewGroup,false);
        WatchStatusViewHolder nvh=new WatchStatusViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(WatchStatusAdapter.WatchStatusViewHolder personViewHolder, int i) {
        personViewHolder.tv_watch_position.setText(watchStatusList.get(i).getPosition()+" 号窗口");
        personViewHolder.tv_watch_workername.setText(watchStatusList.get(i).getWorkerName());
        personViewHolder.tv_watch_good.setText(watchStatusList.get(i).getGood()+"");
        personViewHolder.tv_watch_bad.setText(watchStatusList.get(i).getBad()+"");
        int status = watchStatusList.get(i).getStatus();
        switch(status){
            case 1:
                personViewHolder.tv_watch_status.setText("业务办理中");
                personViewHolder.tv_watch_status.setTextColor(context.getResources().getColor(R.color.orange));
                break;
            case 2:
                personViewHolder.tv_watch_status.setText("在岗");
                personViewHolder.tv_watch_status.setTextColor(context.getResources().getColor(R.color.green_dark));
                break;
            case 3:
                personViewHolder.tv_watch_status.setText("空闲");
                personViewHolder.tv_watch_status.setTextColor(context.getResources().getColor(R.color.red));
                break;

        }

    }

    @Override
    public int getItemCount() {
        return watchStatusList.size();
    }
}