package com.pump.smartbank.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pump.smartbank.R;
import com.pump.smartbank.domain.LoanItem;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanResultFragment extends Fragment {

    @ViewInject(R.id.lv_loan_result)
    private ListView lv_loan_result;

    public LoanResultFragment() {
        // Required empty public constructor
    }

    public interface LoanResultListener{
        void onGetLoanResult(int loanType, List<LoanItem> loanResultList);
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
