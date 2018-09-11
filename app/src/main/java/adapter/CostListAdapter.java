package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hxk.hutuzhang01.R;

import java.util.List;

import Bean.CostBean;

/**
 * Created by hxk on 2018/6/28.
 */

public class CostListAdapter extends BaseAdapter{

    //映射的list
    private List<CostBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    //结合list View把数据输出
    public CostListAdapter(Context context,List<CostBean> list){
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.mTvCostTitle = view.findViewById(R.id.tv_title);
            viewHolder.mTvCostDate = view.findViewById(R.id.tv_date);
            viewHolder.mTvCostMoney = view.findViewById(R.id.tv_cost);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CostBean bean = mList.get(i);//在item属性中设置要显示的数据
        viewHolder.mTvCostTitle.setText(bean.costTitle);
        viewHolder.mTvCostDate.setText(bean.costDate);
        viewHolder.mTvCostMoney.setText(bean.costMoney);
        return view;
    }

    //数据加载
    private static class ViewHolder{
        public TextView mTvCostTitle;
        public TextView mTvCostDate;
        public TextView mTvCostMoney;
    }
}
