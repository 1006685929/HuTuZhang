package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hxk.hutuzhang01.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Bean.CostBean;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by hxk on 2018/6/22.
 */

public class ReportFragment extends Fragment{

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
    private Map<String,Integer> table = new TreeMap<>();
    private LineChartData mData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report,container,false);

        mChart = view.findViewById(R.id.chart);
        mData = new LineChartData();


        List<CostBean> allData = (List<CostBean>) getActivity().getIntent().getSerializableExtra("cost_list");

        reset();
        generateValues(allData);
        generateData();


        return view;
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

    private void generateValues(List<CostBean> allData) {
        if(allData!=null){
            for (int i=0;i<allData.size();i++){
                CostBean costBean = allData.get(i);
                String costDate = costBean.costDate;
                int costMoney = Integer.parseInt(costBean.costMoney);
                if (!table.containsKey(costDate)){
                    table.put(costDate,costMoney);
                }else {
                    int orignMoney = table.get(costDate);
                    table.put(costDate,orignMoney + costMoney);

                }
            }
        }

    }
}
