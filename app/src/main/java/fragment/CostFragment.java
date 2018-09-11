package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hxk.hutuzhang01.R;

import ui.ChartsActivity;
import utils.DateUtils;

/**
 * Created by hxk on 2018/6/22.
 */

public class CostFragment extends Fragment{

    private TextView onlyDay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost,container,false);


        onlyDay = view.findViewById(R.id.onlyDay);
        onlyDay.setText("还有"+DateUtils.thisMonthLeftDay()+"天");



        return view;

    }

}
