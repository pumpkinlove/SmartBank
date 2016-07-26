package com.pump.smartbank.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.function.FunctionActivity;
import com.pump.smartbank.activity.home.HomeActivity;
import com.pump.smartbank.activity.index.IndexActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends TabActivity {
    public static final String TAB_INDEX = "index";
    public static final String TAB_FUNCTION = "function";
    public static final String TAB_HOME = "home";


    @ViewInject(R.id.tab_index)
    private TextView tv_indexTab;
    @ViewInject(R.id.tab_function)
    private TextView tv_functionTab;
    @ViewInject(R.id.tab_home)
    private TextView tv_homeTab;


    private Drawable dIndexPressed;
    private Drawable dIndexNormal;
    private Drawable dFunctionPressed;
    private Drawable dFunctionNormal;
    private Drawable dHomeNormal;
    private Drawable dHomePressed;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        initTabIntents();

    }

    @Event(value={R.id.tab_index,R.id.tab_function,R.id.tab_home},type=View.OnClickListener.class)
    private void onClick(View view) {
        switch(view.getId()){
            case R.id.tab_index:
                pressIndexTab();
                break;
            case R.id.tab_function:
                pressFunctionTab();
                break;
            case R.id.tab_home:
                pressHomeTab();
                break;
        }
    }

    private void pressIndexTab(){
        if (tabHost != null) {
            setIndexTab(true);
            tabHost.setCurrentTabByTag(TAB_INDEX);
            setFunctionTab(false);
            setHomeTab(false);
        }
    }
    private void pressFunctionTab(){
        if (tabHost != null) {
            setFunctionTab(true);
            tabHost.setCurrentTabByTag(TAB_FUNCTION);
            setIndexTab(false);
            setHomeTab(false);
        }
    }
    private void pressHomeTab(){
        if (tabHost != null) {
            setHomeTab(true);
            tabHost.setCurrentTabByTag(TAB_HOME);
            setIndexTab(false);
            setFunctionTab(false);
        }
    }

    private void setIndexTab(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            tv_indexTab.setTextColor(getResources().getColor(R.color.blue_theme_normal));
            if (dIndexPressed == null) {
                dIndexPressed = getResources().getDrawable(R.mipmap.tab1_p);
            }
            drawable = dIndexPressed;
        } else {
            tv_indexTab.setTextColor(getResources().getColor(R.color.gray_light));
            if (dIndexNormal == null) {
                dIndexNormal = getResources().getDrawable(R.mipmap.tab1_n);
            }
            drawable = dIndexNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tv_indexTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void setFunctionTab(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            tv_functionTab.setTextColor(getResources().getColor(R.color.blue_theme_normal));
            if (dFunctionPressed == null) {
                dFunctionPressed = getResources().getDrawable(R.mipmap.tab3_p);
            }
            drawable = dFunctionPressed;
        } else {
            tv_functionTab.setTextColor(getResources().getColor(R.color.gray_light));
            if (dFunctionNormal == null) {
                dFunctionNormal = getResources().getDrawable(R.mipmap.tab3_n);
            }
            drawable = dFunctionNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tv_functionTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void setHomeTab(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            tv_homeTab.setTextColor(getResources().getColor(R.color.blue_theme_normal));
            if (dHomePressed == null) {
                dHomePressed = getResources().getDrawable(R.mipmap.tab4_p);
            }
            drawable = dHomePressed;
        } else {
            tv_homeTab.setTextColor(getResources().getColor(R.color.gray_light));
            if (dHomeNormal == null) {
                dHomeNormal = getResources().getDrawable(R.mipmap.tab4_n);
            }
            drawable = dHomeNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tv_homeTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void initTabIntents() {
        tabHost =  getTabHost();
        TabSpec spec = tabHost.newTabSpec(TAB_INDEX);
        spec.setIndicator(TAB_INDEX);
        Intent indexIntent = new Intent(this, IndexActivity.class);
        spec.setContent(indexIntent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(TAB_FUNCTION);
        spec.setIndicator(TAB_FUNCTION);
        Intent functionIntent = new Intent(this, FunctionActivity.class);
        spec.setContent(functionIntent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(TAB_HOME);
        spec.setIndicator(TAB_HOME);
        Intent homeIntent = new Intent(this, HomeActivity.class);
        spec.setContent(homeIntent);
        tabHost.addTab(spec);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dIndexNormal != null) {
            dIndexNormal.setCallback(null);
        }
        if (dIndexPressed != null) {
            dIndexPressed.setCallback(null);
        }
        if (dFunctionNormal != null) {
            dFunctionNormal.setCallback(null);
        }
        if (dFunctionPressed != null) {
            dFunctionPressed.setCallback(null);
        }
        if (dHomeNormal != null) {
            dHomeNormal.setCallback(null);
        }
        if (dHomePressed != null) {
            dHomePressed.setCallback(null);
        }

    }

}
