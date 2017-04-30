package com.daking.lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseFragment;



public class BettingFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_betting, null);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){



        }
    }
}
