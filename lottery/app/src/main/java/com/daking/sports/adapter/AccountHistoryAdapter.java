package com.daking.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.json.AccountHistoryRsp;

/**
 * Created by Administrator on 2017/6/13.
 */

public class AccountHistoryAdapter extends BaseAdapter {
    private Context mcontext;
    private AccountHistoryRsp accountHistoryRsp;
    private LayoutInflater mInflater;


    public AccountHistoryAdapter(Context mcontext,AccountHistoryRsp accountHistoryRsp) {
        this.mcontext = mcontext;
        this.accountHistoryRsp = accountHistoryRsp;
    }
    @Override
    public int getCount() {
        return null==accountHistoryRsp?0:accountHistoryRsp.getIfo().size();
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
            view = mInflater.inflate(R.layout.adapter_account_history, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_A = (TextView) view.findViewById(R.id.tv_A);
            viewHolder.tv_B = (TextView) view.findViewById(R.id.tv_B);
            viewHolder.tv_C = (TextView) view.findViewById(R.id.tv_C);
            viewHolder.tv_D = (TextView) view.findViewById(R.id.tv_D);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_A.setText(accountHistoryRsp.getIfo().get(position).getDate());
        viewHolder.tv_B.setText(accountHistoryRsp.getIfo().get(position).getBetscore()+"");
        viewHolder.tv_C.setText(accountHistoryRsp.getIfo().get(position).getVgold()+"");
        viewHolder.tv_D.setText(accountHistoryRsp.getIfo().get(position).getM_result()+"");
        return view;
    }

    private class ViewHolder {
        TextView tv_A;
        TextView tv_B;
        TextView tv_C;
        TextView tv_D;
    }

}
