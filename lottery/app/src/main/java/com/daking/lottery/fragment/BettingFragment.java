package com.daking.lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseFragment;
import com.daking.lottery.util.LogUtil;
import com.umeng.analytics.MobclickAgent;


public class BettingFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_betting, null);
        LogUtil.e("===========BettingFragment123=================");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BettingFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BettingFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){



        }
    }
}
