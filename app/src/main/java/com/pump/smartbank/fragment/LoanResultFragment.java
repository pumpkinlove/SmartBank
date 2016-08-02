package com.pump.smartbank.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pump.smartbank.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanResultFragment extends Fragment {

    @ViewInject(R.id.lv_loan_result)
    private ListView lv_loan_result;

    public LoanResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.fragment_loan_result, container, false);
    }


    private void initData(){

    }

    private void initView(){
        x.view().inject(this.getActivity());
    }

}
