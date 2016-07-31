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

import java.util.Collections;
import java.util.List;


/**
 * Created by xu.nan on 2016/7/28.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>{

    private List<Customer> customerList;
    private Context context;

    public CustomerAdapter(List<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context=context;
    }


    //自定义ViewHolder类
    static class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView tv_customer_name;
        TextView tv_customer_come_time;

        public CustomerViewHolder(final View itemView) {
            super(itemView);
            tv_customer_name = (TextView) itemView.findViewById(R.id.tv_customer_name);
            tv_customer_come_time = (TextView) itemView.findViewById(R.id.tv_customer_come_time);
        }

    }
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_customer,viewGroup,false);
        CustomerViewHolder nvh=new CustomerViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.CustomerViewHolder personViewHolder, int i) {
        Collections.reverse(customerList);
        personViewHolder.tv_customer_name.setText(customerList.get(i).getName());
        personViewHolder.tv_customer_come_time.setText(customerList.get(i).getComeTime());
        Collections.reverse(customerList);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}