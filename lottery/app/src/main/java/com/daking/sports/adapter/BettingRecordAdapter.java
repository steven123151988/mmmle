package com.daking.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BettingRecordRsp;
import com.daking.sports.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 下注记录adapter
 */

public class BettingRecordAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mcontext;
    private String ball;
    private List<BettingRecordRsp.IfoBean> mifo;

    public BettingRecordAdapter(Context context, String ball) {
        mcontext = context;
        this.ball = ball;
    }

    public void resetData(int page, List<BettingRecordRsp.IfoBean> ifo) {
        if (null ==mifo) {
            mifo = new ArrayList<BettingRecordRsp.IfoBean>();
        }
        mifo.addAll(ifo);
        if (page == 1) {
            SharePreferencesUtil.addInteger(mcontext, SportsKey.RECORDS_POSITION, 0);
        }else {
            SharePreferencesUtil.addInteger(mcontext, SportsKey.RECORDS_POSITION, mifo.size());
        }
    }

    @Override
    public int getCount() {
        return null ==mifo ? 0 : mifo.size();
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
            view = mInflater.inflate(R.layout.adaper_betting_records, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_A = (TextView) view.findViewById(R.id.tv_A);
            viewHolder.tv_B = (TextView) view.findViewById(R.id.tv_B);
            viewHolder.tv_C = (TextView) view.findViewById(R.id.tv_C);
            viewHolder.tv_D = (TextView) view.findViewById(R.id.tv_D);
            viewHolder.tv_E = (TextView) view.findViewById(R.id.tv_E);
            viewHolder.tv_F = (TextView) view.findViewById(R.id.tv_F);
            viewHolder.tv_G = (TextView) view.findViewById(R.id.tv_G);
            viewHolder.tv_H = (TextView) view.findViewById(R.id.tv_H);
            viewHolder.tv_I = (TextView) view.findViewById(R.id.tv_I);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        switch (ball) {
            case SportsKey.FOOTBALL:
                viewHolder.tv_A.setText(mcontext.getString(R.string.football) + " [ " + mifo.get(position).getBetType() + " ]");
                break;
            case SportsKey.BASKETBALL:
                viewHolder.tv_A.setText(mcontext.getString(R.string.basketball) + " [ " + mifo.get(position).getBetType() + " ]");
                break;

        }
        viewHolder.tv_B.setText("时间：" + mifo.get(position).getBetTime());
        viewHolder.tv_C.setText("单号：" + mifo.get(position).getID());
        if (mifo.get(position).getMiddle().getLeag().equals("")){
            viewHolder.tv_D.setVisibility(view.GONE);
        }else{
            viewHolder.tv_D.setVisibility(View.VISIBLE);
            viewHolder.tv_D.setText(mifo.get(position).getMiddle().getLeag());
        }

        if (mifo.get(position).getMiddle().getMid().equals("")){
            viewHolder.tv_E.setVisibility(view.GONE);
        }else{
            viewHolder.tv_E.setVisibility(View.VISIBLE);
            viewHolder.tv_E.setText(mifo.get(position).getMiddle().getMid());
        }

        if (mifo.get(position).getMiddle().getTeam().equals("")){
            viewHolder.tv_F.setVisibility(view.GONE);
        }else{
            viewHolder.tv_F.setVisibility(View.VISIBLE);
            viewHolder.tv_F.setText(mifo.get(position).getMiddle().getTeam());
        }
        if (mifo.get(position).getMiddle().getRate().equals("")){
            viewHolder.tv_G.setVisibility(view.GONE);
        }else{
            viewHolder.tv_G.setVisibility(View.VISIBLE);
            viewHolder.tv_G.setText(mifo.get(position).getMiddle().getRate());
        }
        viewHolder.tv_H.setText("投注: " + mifo.get(position).getBetScore());
        viewHolder.tv_I.setText("可赢: " + mifo.get(position).getGwin());
        return view;
    }

    private class ViewHolder {
        TextView tv_A;
        TextView tv_B;
        TextView tv_C;
        TextView tv_D;
        TextView tv_E;
        TextView tv_F;
        TextView tv_G;
        TextView tv_H;
        TextView tv_I;
    }
}
