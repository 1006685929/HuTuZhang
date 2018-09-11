package ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hxk.hutuzhang01.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Bean.CostBean;
import fragment.CostFragment;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by hxk on 2018/7/5.
 */

public class ChartsActivity extends AppCompatActivity{

    public int spend = 0;

    private int numberOfPoints = 12;
    //private LineChartView chart;
    private int numberOfLines = 1;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;

    private LineChartView mChart;
    private Map<String,Integer> table = new TreeMap<>();//table存放数据，日期和金额
    private LineChartData mData;

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts);

        mChart = findViewById(R.id.charts);
        mData =new LineChartData();
        List<CostBean> allData = (List<CostBean>)getIntent().getSerializableExtra("cost_list");


        reset();
        generateValues(allData);
        generateData();


    }


    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        shape = ValueShape.CIRCLE;
        isFilled = false;
        hasLabels = false;
        isCubic = false;
        hasLabelForSelected = true;
        pointsHaveDifferentColor = false;

        mChart.setValueSelectionEnabled(hasLabelForSelected);
        resetViewport();
    }

    //坐标轴
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(mChart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = numberOfPoints - 1;
        mChart.setMaximumViewport(v);
        mChart.setCurrentViewport(v);
    }


    //数据
    private void generateData() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int indeX = 0;
        for (Integer value: table.values()){
            values.add(new PointValue(indeX,value));
            indeX++;
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[1]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[2]);

        line.setCubic(isCubic);
        line.setFilled(isFilled);
        line.setHasLabels(hasLabels);
        line.setHasLabelsOnlyForSelected(hasLabelForSelected);
        line.setHasLines(hasLines);
        line.setHasPoints(hasPoints);
        lines.add(line);

        mData = new LineChartData(lines);

        if (hasAxes){
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames){
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            mData.setAxisXBottom(axisX);
            mData.setAxisYLeft(axisY);
        }else {
            mData.setAxisXBottom(null);
            mData.setAxisYLeft(null);
        }
        mData.setBaseValue(Float.NEGATIVE_INFINITY);


        mChart.setLineChartData(mData);


    }

    //数据累加处理
    private void generateValues(List<CostBean> allData) {
        if(allData!=null){
            for (int i=0;i<allData.size();i++){
                CostBean costBean = allData.get(i);
                String costDate = costBean.costDate;//抽取日期
                int costMoney = Integer.parseInt(costBean.costMoney);
                spend=spend+costMoney;
                if (!table.containsKey(costDate)){//判断日期是否重复
                    table.put(costDate,costMoney);
                }else {
                    int orignMoney = table.get(costDate);
                    table.put(costDate,orignMoney + costMoney);

                }
            }
            //System.out.println("消费了："+spend);
        }

    }

}