package com.pump.smartbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.Customer;
import com.pump.smartbank.domain.WatchStatus;
import com.pump.smartbank.listener.MyItemListener;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Collections;
import java.util.List;


/**
 * Created by xu.nan on 2016/7/28.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>{

    private List<Customer> customerList;
    private Context context;
    private MyItemListener customerItemListener;

    public CustomerAdapter(List<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context=context;
    }

    public void setOnItemClickListener(MyItemListener customerItemListener){
        this.customerItemListener = customerItemListener;
    }

    //自定义ViewHolder类
    static class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @ViewInject(R.id.tv_customer_name)
        TextView tv_customer_name;
        @ViewInject(R.id.tv_customer_come_time)
        TextView tv_customer_come_time;
        @ViewInject(R.id.tv_customer_business)
        private TextView tv_customer_business;

        private MyItemListener customerItemListener;

        public CustomerViewHolder(final View itemView, MyItemListener customerItemListener ) {
            super(itemView);
            x.view().inject(this, itemView);
            this.customerItemListener = customerItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(customerItemListener != null){
                customerItemListener.onItemClick(view,getPosition());
            }
        }
    }
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_customer,viewGroup,false);
        CustomerViewHolder cvh = new CustomerViewHolder(v, customerItemListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.CustomerViewHolder personViewHolder, int i) {
        Collections.reverse(customerList);
        personViewHolder.tv_customer_name.setText(customerList.get(i).getCustomname());
        personViewHolder.tv_customer_come_time.setText(customerList.get(i).getComeTime());
        personViewHolder.tv_customer_business.setText(customerList.get(i).getBusiness());
        if('贵' == customerList.get(i).getBusiness().charAt(0)){
            personViewHolder.tv_customer_business.setTextColor(context.getResources().getColor(R.color.orange));
        }else{
            personViewHolder.tv_customer_business.setTextColor(context.getResources().getColor(R.color.dark));
        }
        Collections.reverse(customerList);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}