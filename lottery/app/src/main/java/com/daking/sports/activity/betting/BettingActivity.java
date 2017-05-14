package com.daking.sports.activity.betting;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.adapter.MyExpandableListAdapter;
import com.daking.sports.base.BaseActivity;

/**
 * Created by 18 on 2017/5/12.  足球篮球下下注面页
 */

public class BettingActivity  extends BaseActivity implements View.OnClickListener{
    private ExpandableListView lv_betting;
    private MyExpandableListAdapter myExpandableListAdapter;
    private TextView tv_center;
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText("足球");
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);

        lv_betting=(ExpandableListView)findViewById(R.id.lv_betting);
        lv_betting.setGroupIndicator(null);
        myExpandableListAdapter=new MyExpandableListAdapter(mContext);
        lv_betting.setAdapter(myExpandableListAdapter);

        //让2级菜单全部展开
        for (int i=0; i<3; i++) {
            lv_betting.expandGroup(i);
        }

        lv_betting.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {



                return true;
            }
        });





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
              finish();
                break;
        }

    }
}
