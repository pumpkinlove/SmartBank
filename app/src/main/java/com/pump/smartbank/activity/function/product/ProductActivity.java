package com.pump.smartbank.activity.function.product;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Product;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_product)
public class ProductActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.product_content)
    private TextView tv_content;
    @ViewInject(R.id.product_term)
    private TextView tv_term;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    @Override
    protected void initData() {
        product = (Product) getIntent().getSerializableExtra("product");
        if(product == null){
            product = new Product();
        }
    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText(product.getName());
        tv_leftContent.setVisibility(View.VISIBLE);
        tv_content.setText(product.getContent());
        tv_term.setText(product.getTerm());
    }

    @Event(value={R.id.tv_leftContent},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
        }
    }
}
