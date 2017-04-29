package com.daking.lottery.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseFragment;



public class BettingFragment extends BaseFragment implements View.OnClickListener {
    // Fragment管理器
    private FragmentManager mFragmentManager;
    // fragment事物
    private FragmentTransaction mFragmentTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menufragment, null);
        view.findViewById(R.id.gotoGame).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.gotoGame:



                break;


        }
    }
}
