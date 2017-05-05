package com.daking.lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by 18 on 2017/5/4.
 */

public class PrizeFragment extends BaseFragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prize, null);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PrizeFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PrizeFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){



        }
    }
}
