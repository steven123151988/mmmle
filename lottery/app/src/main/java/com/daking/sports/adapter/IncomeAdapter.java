package com.daking.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.json.IncomeRep;

/**
 * Created by Administrator on 2017/6/13.
 */

public class IncomeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mcontext;
    private IncomeRep mincomeRep;

    public IncomeAdapter(Context context,IncomeRep incomeRep) {
        mcontext = context;
        mincomeRep=incomeRep;
    }

    @Override
    public int getCount() {
        return null==mincomeRep?0:mincomeRep.getIfo().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        mInflater = LayoutInflater.from(mcontext);
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_income_records, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_A = (TextView) view.findViewById(R.id.tv_A);
            viewHolder.tv_B = (TextView) view.findViewById(R.id.tv_B);
            viewHolder.tv_C = (TextView) view.findViewById(R.id.tv_C);
            viewHolder.tv_D = (TextView) view.findViewById(R.id.tv_D);
            viewHolder.tv_E= (TextView) view.findViewById(R.id.tv_E);
            viewHolder.tv_F = (TextView) view.findViewById(R.id.tv_F);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_A.setText(mincomeRep.getIfo().get(position).getID());
        viewHolder.tv_B.setText(mincomeRep.getIfo().get(position).getDate());
        viewHolder.tv_C.setText(mincomeRep.getIfo().get(position).getGold());
        viewHolder.tv_D.setText(mincomeRep.getIfo().get(position).getType());
        viewHolder.tv_E.setText(mincomeRep.getIfo().get(position).getStatus());
        viewHolder.tv_F.setText(mincomeRep.getIfo().get(position).getRemark());

        return view;
    }

    private class ViewHolder {
        TextView tv_A;
        TextView tv_B;
        TextView tv_C;
        TextView tv_D;
        TextView tv_E;
        TextView tv_F;

    }
}
