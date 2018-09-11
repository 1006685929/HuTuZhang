package fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hxk.hutuzhang01.MainActivity;
import com.example.hxk.hutuzhang01.R;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Bean.CostBean;
import adapter.CostListAdapter;
import ui.ChartsActivity;
import utils.DatabaseHelper;

/**
 * Created by hxk on 2018/6/22.
 */

public class BillFragment extends Fragment{

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;

    private SwipeRefreshLayout fresh;

    private TextView bi;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill,container,false);

         mDatabaseHelper = new DatabaseHelper(getActivity());
         mCostBeanList = new ArrayList<>();
         final ListView costList = view.findViewById(R.id.bill_list);
         initCostDate();
         mAdapter = new CostListAdapter(getActivity(), mCostBeanList);
         costList.setAdapter(mAdapter);

        costList.setOnCreateContextMenuListener(listviewLongPress);

        fresh = view.findViewById(R.id.fresh);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCostBeanList.removeAll(mCostBeanList);
                        initCostDate();
                        mAdapter.notifyDataSetChanged();

                        //EventBus.getDefault().post(mCostBeanList);

                        //Intent intent = new Intent(getActivity(), ReportFragment.class);
                        //intent.putExtra("cost_list",(Serializable)mCostBeanList);
                        //getActivity().startActivity(intent);

                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        fresh.setRefreshing(false);
                    }
                },1000);
            }
        });


        bi = view.findViewById(R.id.bi);
        bi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChartsActivity.class);
                intent.putExtra("cost_list",(Serializable)mCostBeanList);
                startActivity(intent);
            }
        });


        return view;
    }

    View.OnCreateContextMenuListener listviewLongPress = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
            new AlertDialog.Builder(getActivity()).setTitle("是否删除此记录")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int mListPos = info.position;//获取在列表中的位置
                            mDatabaseHelper.deleteAllData();

                            //mDatabaseHelper.del(id);
                            mCostBeanList.remove(mListPos);
                            mAdapter.notifyDataSetChanged();
                        }
                    }).show();
        }
    };


    private void initCostDate() {
 //       mDatabaseHelper.deleteAllData();
//        for (int i=0;i<6;i++){
//            CostBean costBean = new CostBean();
//            costBean.costTitle = "book";
//            costBean.costDate = "911";
//            costBean.costMoney = "100";
//            mDatabaseHelper.insertCost(costBean);
//        }
        Cursor cursor = mDatabaseHelper.getAllCostData();
        if (cursor!= null){
            while (cursor.moveToNext()){
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }

//    private void delCostDate() {
//        Cursor cursor = mDatabaseHelper.getAllCostData();
//        if (cursor!= null){
//            while (cursor.moveToNext()){
//                CostBean costBean = new CostBean();
//                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
//                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
//                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
//                mCostBeanList.remove(costBean);
//            }
//            cursor.close();
//        }
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
