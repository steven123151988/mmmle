package com.daking.sports.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 18 on 2017/5/4.
 */

public class ScoreFragment extends BaseFragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, null);
        ImageView iv_center=(ImageView) view.findViewById(R.id.iv_center);
        iv_center.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ScoreFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ScoreFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){



        }
    }
}
