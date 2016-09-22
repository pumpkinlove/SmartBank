package com.pump.smartbank.activity.function.analyze;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pump.smartbank.R;
import com.pump.smartbank.activity.BaseActivity;
import com.pump.smartbank.domain.Config;
import com.pump.smartbank.util.DateUtil;
import com.pump.smartbank.util.DbUtil;
import com.pump.smartbank.util.XUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.config.DbConfigs;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

@ContentView(R.layout.activity_analyze)
public class AnalyzeActivity extends BaseActivity {

    @ViewInject(R.id.tv_middleContent)
    private TextView tv_middleContent;

    @ViewInject(R.id.tv_leftContent)
    private TextView tv_leftContent;

    @ViewInject(R.id.chart)
    private ColumnChartView chart;
    @ViewInject(R.id.chart_preview)
    private PreviewColumnChartView previewChart;

    private ColumnChartData data;
    private ColumnChartData previewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        initData();
        initView();

        generateDefaultData();
        chart.setColumnChartData(data);
        // Disable zoom/scroll for previewed chart, visible chart ranges depends on preview chart viewport so
        // zoom/scroll is unnecessary.
        chart.setZoomEnabled(false);
        chart.setScrollEnabled(false);
        previewChart.setColumnChartData(previewData);
        previewChart.setViewportChangeListener(new ViewportListener());

        previewX(false);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        x.view().inject(this);
        tv_middleContent.setText("客流量分析");
        tv_leftContent.setVisibility(View.VISIBLE);
    }

    @Event(value={R.id.tv_leftContent},type=View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_leftContent:
                finish();
                break;
        }
    }


    private void generateDefaultData() {
        int numSubcolumns = 1;
        int numColumns = 30;
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            columns.add(new Column(values));
        }

        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));

        // prepare preview data, is better to use separate deep copy for preview chart.
        // set color to grey to make preview area more visible.
        previewData = new ColumnChartData(data);
        for (Column column : previewData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }

    }

    private class ViewportListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {
            // don't use animation, it is unnecessary when using preview chart because usually viewport changes
            // happens to often.
            chart.setCurrentViewport(newViewport);
        }

    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
        previewChart.setZoomType(ZoomType.HORIZONTAL);
    }

//    @Event(value = R.id.btn_analyze ,type=View.OnClickListener.class)
//    private void analyze(View view){
//        try {
//            DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
//            DbManager manager = x.getDb(daoConfig);
//            Config config = manager.findFirst(Config.class);
//            String url = "http://"+config.getHttpIp()+":"+config.getHttpPort()+"/CIIPS_A/analyze/countAll.action";
//            Map<String, Object> params = new HashMap<>();
//            params.put("opdateTo", DateUtil.toMonthDay(new Date()));
//            params.put("opdateFrom", DateUtil.getSpecifiedDayBefore(new Date(), 30));
//            params.put("business","个人账户申请书");
//            XUtil.Post(url, params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    Log.e("-=======sssss=======",result);
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Toast.makeText(x.app(),"网络开小差啦，请稍后再试>_<",Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
