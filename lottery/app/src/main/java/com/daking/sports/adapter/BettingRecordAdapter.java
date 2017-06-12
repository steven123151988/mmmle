package com.daking.sports.adapter;

import android.content.Context;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.json.BettingRecordRsp;

/**
 *   下注记录adapter
 */

public class BettingRecordAdapter  extends BaseAdapter{
    private LayoutInflater mInflater;
    private Context mcontext;
    private BettingRecordRsp mbettingRecordRsp;
    private String ball;

    public BettingRecordAdapter(Context context,BettingRecordRsp bettingRecordRsp,String ball){
        mcontext=context;
        mbettingRecordRsp=bettingRecordRsp;
        this.ball=ball;
    }
    @Override
    public int getCount() {
        return null==mbettingRecordRsp?0:mbettingRecordRsp.getIfo().size();
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
        mInflater = LayoutInflater.from(mcontext);//写在这里结局了动画还没加载完点击其他地方导致的bug？等待填充数据的时间验证
        if (view == null) {
            view = mInflater.inflate(R.layout.adaper_betting_records, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_A = (TextView) view.findViewById(R.id.tv_A);
            viewHolder.tv_B = (TextView) view.findViewById(R.id.tv_B);
            viewHolder.tv_C = (TextView) view.findViewById(R.id.tv_C);
            viewHolder.tv_D = (TextView) view.findViewById(R.id.tv_D);
            viewHolder.tv_E = (TextView) view.findViewById(R.id.tv_E);
            viewHolder.tv_F = (TextView) view.findViewById(R.id.tv_F);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.tv_A.setText(ball+"["+mbettingRecordRsp.getIfo().get(position).getBetType()+"]");
        viewHolder.tv_B.setText("时间："+mbettingRecordRsp.getIfo().get(position).getBetTime());
        viewHolder.tv_C.setText("单号："+mbettingRecordRsp.getIfo().get(position).getID());
        viewHolder.tv_D.setText(mbettingRecordRsp.getIfo().get(position).getMiddle());
        viewHolder.tv_E.setText("投注: "+mbettingRecordRsp.getIfo().get(position).getBetScore());
        viewHolder.tv_F.setText("可盈: "+mbettingRecordRsp.getIfo().get(position).getGwin());

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
