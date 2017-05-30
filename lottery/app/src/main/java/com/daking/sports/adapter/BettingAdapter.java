package com.daking.sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.betting.BettingActivity;
import com.daking.sports.json.FootballGQRsp;

import java.util.List;

/**
 * Created by 18 on 2017/5/8.  足球篮球下注的适配器
 */

public class BettingAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mcontext;
    private List<FootballGQRsp.IfoBean> ifos;

    public BettingAdapter(Context context, List<FootballGQRsp.IfoBean> ifo) {
        this.mcontext = context;
        this.ifos=ifo;
    }

    @Override
    public int getCount() {
        return null==ifos?0:ifos.size();
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
            view = mInflater.inflate(R.layout.adapter_sports_betting, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            viewHolder.tv_2 = (TextView) view.findViewById(R.id.tv_2);
            viewHolder.tv_3 = (TextView) view.findViewById(R.id.tv_3);
            viewHolder.tv_4 = (TextView) view.findViewById(R.id.tv_4);
            viewHolder.tv_5 = (TextView) view.findViewById(R.id.tv_5);
            viewHolder.tv_6 = (TextView) view.findViewById(R.id.tv_6);
            viewHolder.ll_betting = (LinearLayout) view.findViewById(R.id.rl_betting);
            viewHolder.ll_betting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mcontext, BettingActivity.class);
                    intent.putExtra("","");
                    mcontext.startActivity(intent);
                }
            });
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_1.setText(ifos.get(position).getM_League());
        viewHolder.tv_2.setText("2");
        viewHolder.tv_3.setText("3");
        viewHolder.tv_4.setText(ifos.get(position).getMB_Team());
        viewHolder.tv_5.setText(ifos.get(position).getTG_Team());
        viewHolder.tv_6.setText(ifos.get(position).getMB_Ball()+" : "+ifos.get(position).getTG_Ball());
        return view;
    }

    class ViewHolder {
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        LinearLayout ll_betting;
    }
}
