package fragment;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hxk.hutuzhang01.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Bean.CostBean;
import adapter.CostListAdapter;
import utils.DatabaseHelper;

/**
 * Created by hxk on 2018/6/22.
 */

public class RecordFragment extends DialogFragment implements View.OnClickListener{

  private ImageView tb_note_remark;
  private TextView choose_date;
  private TextView tb_calc_num_0;
  private TextView tb_calc_num_1;
  private TextView tb_calc_num_2;
  private TextView tb_calc_num_3;
  private TextView tb_calc_num_4;
  private TextView tb_calc_num_5;
  private TextView tb_calc_num_6;
  private TextView tb_calc_num_7;
  private TextView tb_calc_num_8;
  private TextView tb_calc_num_9;
  private TextView tb_calc_num_dot;
  private TextView tb_note_money;
  private TextView tb_calc_num_done;
  private RelativeLayout tb_calc_num_del;
  private EditText tv_note;

  protected String remarkInput = "";//备注
  protected AlertDialog alertDialog;//备注对话框

    private DatabaseHelper mDatabaseHelper;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record,container,false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化按键对象
        initView();
        //添加监听
        setOnClick();
        //初始化日期

    }


    private void setOnClick() {
        tb_note_remark.setOnClickListener(this);
        choose_date.setOnClickListener(this);
        tb_calc_num_0.setOnClickListener(this);
        tb_calc_num_1.setOnClickListener(this);
        tb_calc_num_2.setOnClickListener(this);
        tb_calc_num_3.setOnClickListener(this);
        tb_calc_num_4.setOnClickListener(this);
        tb_calc_num_5.setOnClickListener(this);
        tb_calc_num_6.setOnClickListener(this);
        tb_calc_num_7.setOnClickListener(this);
        tb_calc_num_8.setOnClickListener(this);
        tb_calc_num_9.setOnClickListener(this);
        tb_calc_num_dot.setOnClickListener(this);
        tb_calc_num_del.setOnClickListener(this);
        tb_calc_num_done.setOnClickListener(this);
        tv_note.setOnClickListener(this);
    }

    private void initView() {
        tb_note_remark = getView().findViewById(R.id.tb_note_remark);
        choose_date = getView().findViewById(R.id.choose_date);
        tb_calc_num_0 = getView().findViewById(R.id.tb_calc_num_0);
        tb_calc_num_1 = getView().findViewById(R.id.tb_calc_num_1);
        tb_calc_num_2 = getView().findViewById(R.id.tb_calc_num_2);
        tb_calc_num_3 = getView().findViewById(R.id.tb_calc_num_3);
        tb_calc_num_4 = getView().findViewById(R.id.tb_calc_num_4);
        tb_calc_num_5 = getView().findViewById(R.id.tb_calc_num_5);
        tb_calc_num_6 = getView().findViewById(R.id.tb_calc_num_6);
        tb_calc_num_7 = getView().findViewById(R.id.tb_calc_num_7);
        tb_calc_num_8 = getView().findViewById(R.id.tb_calc_num_8);
        tb_calc_num_9 = getView().findViewById(R.id.tb_calc_num_9);
        tb_calc_num_dot = getView().findViewById(R.id.tb_calc_num_dot);
        tb_calc_num_done = getView().findViewById(R.id.tb_calc_num_done);
        tb_calc_num_del = getView().findViewById(R.id.tb_calc_num_del);
        tb_note_money = getView().findViewById(R.id.tb_note_money);
        tv_note = getView().findViewById(R.id.tv_note);
    }

    @Override
    public void onClick(View view) {

        String str = tb_note_money.getText().toString();
       switch (view.getId()){
           case R.id.tb_calc_num_0:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_1:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_2:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_3:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_4:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_5:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_6:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_7:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_8:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_9:
               tb_note_money.setText(str+((TextView)view).getText());
               break;
           case R.id.tb_calc_num_dot:
               if (!str.contains(".")){
                   tb_note_money.setText(str+((TextView)view).getText());
               }else {
                   tb_note_money.setEnabled(false);
               }
               break;
           case R.id.tb_calc_num_done:
               CostBean costBean = new CostBean();
               costBean.costTitle = tv_note.getText().toString();
               costBean.costDate = choose_date.getText().toString();
               costBean.costMoney = tb_note_money.getText().toString();
               mDatabaseHelper.insertCost(costBean);

//               BillFragment bf = new BillFragment();
//               bf.mCostBeanList = new ArrayList<>();
//               bf.mAdapter = new CostListAdapter(getActivity(), bf.mCostBeanList);
//               bf.mCostBeanList.add(costBean);
//               bf.mAdapter.notifyDataSetChanged();

               Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
               break;
           case R.id.tb_calc_num_del:
               if(str!=null&&!str.equals("")){
                   tb_note_money.setText(str.substring(0,str.length()-1));
               }
               break;
           case R.id.tb_note_remark:
               showContentDialog();
               break;
           case R.id.choose_date:
               showTimeSelector();
               break;
               default:
                   break;
        }
    }
//日期选择器
    private void showTimeSelector() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                choose_date.setText(i + "-" + (i1 + 1) + "-" + (i2));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showContentDialog() {
        Toast.makeText(getActivity(), "点击了备注按钮", Toast.LENGTH_SHORT).show();
    }


}
