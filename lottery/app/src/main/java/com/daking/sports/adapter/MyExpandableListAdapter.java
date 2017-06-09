package com.daking.sports.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.json.BettingDetailRsp;

import java.util.List;


/**
 * Created by 18 on 2017/5/14. 体育投注面页（扩展性的listview）
 */

public class MyExpandableListAdapter implements ExpandableListAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private BettingDetailRsp bettingDetailRsp;
    private List<BettingDetailRsp.IfoBean.BetmsgBean> betmsg;
    private List<BettingDetailRsp.IfoBean.BetmsgBean.DataBean> dataBeen;

    public MyExpandableListAdapter(Context context, BettingDetailRsp bettingDetailRsp) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.bettingDetailRsp = bettingDetailRsp;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return null == bettingDetailRsp.getIfo().getBetmsg() ? 0 : bettingDetailRsp.getIfo().getBetmsg().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return null == bettingDetailRsp.getIfo().getBetmsg().get(groupPosition).getData() ?
                0 : bettingDetailRsp.getIfo().getBetmsg().get(groupPosition).getData().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        TitleViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new TitleViewHolder();
            view = mInflater.inflate(R.layout.adapter_betting_title, null);
            viewHolder.iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TitleViewHolder) view.getTag();
        }
        betmsg = bettingDetailRsp.getIfo().getBetmsg();
        viewHolder.tv_title.setText(betmsg.get(groupPosition).getTitle());
        if (isExpanded) {
            viewHolder.iv_arrow.setImageResource(R.mipmap.arrow_up);
        } else {
            viewHolder.iv_arrow.setImageResource(R.mipmap.arrow_down);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.adapter_betting_detail, null);
            viewHolder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            viewHolder.tv_2 = (TextView) view.findViewById(R.id.tv_2);
            viewHolder.tv_3 = (TextView) view.findViewById(R.id.tv_3);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        dataBeen= bettingDetailRsp.getIfo().getBetmsg().get(groupPosition).getData();
        viewHolder.tv_1.setText(dataBeen.get(childPosition).getTeam());
        viewHolder.tv_2.setText(dataBeen.get(childPosition).getMid());
        viewHolder.tv_3.setText(dataBeen.get(childPosition).getRate());
        return view;
    }

    /**
     * ExpandableListView 如果子条目需要响应click事件,必需返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private class TitleViewHolder {
        TextView tv_title;
        ImageView iv_arrow;
    }

    private class ViewHolder {
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
    }
}
