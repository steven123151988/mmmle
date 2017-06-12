package com.daking.sports.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.betting.BettingDetailActivity;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BallGQRsp;

import java.util.List;

/**
 * Created by 18 on 2017/5/8.  足球篮球下注的适配器
 */

public class BettingAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mcontext;
    private List<BallGQRsp.IfoBean> ifos;
    private String ball;

    public BettingAdapter(Context context, List<BallGQRsp.IfoBean> ifo, String ball) {
        this.mcontext = context;
        this.ifos = ifo;
        this.ball = ball;
    }

    @Override
    public int getCount() {
        return null == ifos ? 0 : ifos.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
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
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_1.setText(ifos.get(position).getM_League());
        viewHolder.tv_2.setText(ifos.get(position).getCourse());
        viewHolder.tv_3.setText(ifos.get(position).getStime());
        viewHolder.tv_4.setText(ifos.get(position).getMB_Team());
        viewHolder.tv_5.setText(ifos.get(position).getTG_Team());
        viewHolder.tv_6.setText(ifos.get(position).getResult());
        final String ballteam = ifos.get(position).getMB_Team() + " VS " + ifos.get(position).getTG_Team();
        viewHolder.ll_betting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, BettingDetailActivity.class);
                intent.putExtra(SportsKey.MID, ifos.get(position).getMID());
                intent.putExtra(SportsKey.BALL_TEAM, ballteam);
                intent.putExtra(SportsKey.BALL, ball);
                intent.putExtra(SportsKey.TYPE, ifos.get(position).getGq());
                mcontext.startActivity(intent);
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        LinearLayout ll_betting;
    }
}
