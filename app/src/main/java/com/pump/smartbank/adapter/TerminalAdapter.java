package com.pump.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.Terminal;
import com.pump.smartbank.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Collections;
import java.util.List;


/**
 * Created by xu.nan on 2016/7/28.
 */
public class TerminalAdapter extends RecyclerView.Adapter<TerminalAdapter.NoticeViewHolder>{

    private List<Terminal> terminalList;
    private Context context;

    public TerminalAdapter(List<Terminal> terminalList, Context context) {
        this.terminalList = terminalList;
        this.context=context;
    }

    //自定义ViewHolder类
    static class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView tv_terminalid;
        TextView tv_deptno;
        Button btn_subscribe;

        public NoticeViewHolder(final View itemView) {
            super(itemView);
            tv_terminalid = (TextView) itemView.findViewById(R.id.tv_terminal_id);
            tv_deptno = (TextView) itemView.findViewById(R.id.tv_terminal_deptno);
            btn_subscribe = (Button) itemView.findViewById(R.id.btn_subscribe);

        }

    }
    @Override
    public TerminalAdapter.NoticeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_terminal,viewGroup,false);
        NoticeViewHolder nvh = new NoticeViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(TerminalAdapter.NoticeViewHolder personViewHolder, int i) {
        final int position = i;
//        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
//        final DbManager dbManager = x.getDb(daoConfig);
        personViewHolder.tv_terminalid.setText(terminalList.get(i).getTerminalno());
        personViewHolder.tv_deptno.setText(terminalList.get(i).getTerminaltype());
        if(!terminalList.get(i).isSubscribe()) {
            personViewHolder.btn_subscribe.setText("未订阅");
            personViewHolder.btn_subscribe.setBackground(context.getResources().getDrawable(R.drawable.red_button_selector));
        }else{
            personViewHolder.btn_subscribe.setText("已订阅");
            personViewHolder.btn_subscribe.setBackground(context.getResources().getDrawable(R.drawable.blue_button_selector));
        }
        personViewHolder.btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(terminalList.get(position).isSubscribe()) {
                    Log.e("----case1","1111");
                    terminalList.get(position).setSubscribe(false);
                    ((Button) view).setText("未订阅");
                    ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.red_button_selector));
                }else{
                    Log.e("----case2","2222");
                    terminalList.get(position).setSubscribe(true);
                    ((Button) view).setText("已订阅");
                    ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.blue_button_selector));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return terminalList.size();
    }

}