package com.pump.smartbank.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.LoanItem;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class LoanItemAdapter extends BaseAdapter {

    private Context context;
    private List<LoanItem> loanItemList;

    public LoanItemAdapter(Context context, List<LoanItem> loanItemList) {
        this.context = context;
        this.loanItemList = loanItemList;
    }

    @Override
    public int getCount() {
        return loanItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return loanItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view=null;
        try{
            // 使用堆内存中的唯一的一份字节码（ListView的优化）
            ViewHolder holder = new ViewHolder();
            // 复用缓存（ListView的优化）
            if (convertView == null) {
                view = View.inflate(context, R.layout.item_loan, null);
                holder = new ViewHolder();
                holder.tv_loan_item_period = (TextView) view.findViewById(R.id.loan_item_period);
                holder.tv_loan_item_total=(TextView) view.findViewById(R.id.loan_item_total);
                holder.tv_loan_item_principal=(TextView) view.findViewById(R.id.loan_item_principal);
                holder.tv_loan_item_interest=(TextView) view.findViewById(R.id.loan_item_interest);
                holder.tv_loan_item_principal_balance=(TextView) view.findViewById(R.id.loan_item_principal_balance);
                view.setTag(holder);
            } else {
                // 使用缓存的view
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            LoanItem loanItem = loanItemList.get(i);
            if(loanItem != null){
                holder.tv_loan_item_period.setText(loanItem.getPeriod());
                holder.tv_loan_item_total.setText(loanItem.getTotal()+"");
                holder.tv_loan_item_principal.setText(loanItem.getPrincipal()+"");
                holder.tv_loan_item_interest.setText(loanItem.getInterest()+"");
                holder.tv_loan_item_principal_balance.setText(loanItem.getPrincipalBalance()+"");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }


    static class ViewHolder{
        TextView tv_loan_item_period;
        TextView tv_loan_item_total;
        TextView tv_loan_item_principal;
        TextView tv_loan_item_interest;
        TextView tv_loan_item_principal_balance;

    }
}
