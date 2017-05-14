package com.daking.sports.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;


/**
 * Created by 18 on 2017/5/14. 体育投注面页（扩展性的listview）
 */

public class MyExpandableListAdapter implements ExpandableListAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public MyExpandableListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return 5;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        TitleViewHolder viewHolder = null;
        if (view == null) {
            viewHolder=new TitleViewHolder();
            view = mInflater.inflate(R.layout.adapter_betting_title, null);
            viewHolder.iv_arrow=(ImageView) view.findViewById(R.id.iv_arrow);
            viewHolder.tv_title=(TextView) view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TitleViewHolder) view.getTag();
        }

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
            viewHolder=new ViewHolder();
            view = mInflater.inflate(R.layout.adapter_betting_detail, null);
            viewHolder.tv_1=(TextView) view.findViewById(R.id.tv_1);
            viewHolder.tv_2=(TextView) view.findViewById(R.id.tv_2);
            viewHolder.tv_3=(TextView) view.findViewById(R.id.tv_3);
            viewHolder.tv_4=(TextView) view.findViewById(R.id.tv_4);
            viewHolder.tv_5=(TextView) view.findViewById(R.id.tv_5);
            viewHolder.tv_6=(TextView) view.findViewById(R.id.tv_6);
            viewHolder.tv_7=(TextView) view.findViewById(R.id.tv_7);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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

    class TitleViewHolder {
        TextView tv_title;
        ImageView iv_arrow;
    }

    class ViewHolder{
        TextView tv_1;
        TextView tv_2;
        TextView tv_3;
        TextView tv_4;
        TextView tv_5;
        TextView tv_6;
        TextView tv_7;

    }
}
