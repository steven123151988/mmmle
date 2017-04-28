package com.xhqb.lotteryandsports.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xhqb.lotteryandsports.R;
import com.xhqb.lotteryandsports.base.BaseFragment;
import com.xhqb.lotteryandsports.fragment.GameFragment;
/**
 * Created by quchuangye on 2017/4/7.
 */

public class MenuFragment extends BaseFragment implements View.OnClickListener {
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
                GameFragment GameFragment=new GameFragment();
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.view_fragment, GameFragment);
                mFragmentTransaction.commitAllowingStateLoss();


                break;


        }
    }
}
