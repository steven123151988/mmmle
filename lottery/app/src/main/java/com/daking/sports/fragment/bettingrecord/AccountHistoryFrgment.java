package com.daking.sports.fragment.bettingrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;

/**
 * Created by 18 on 2017/5/11. 账户历史面页
 */

public class AccountHistoryFrgment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounthistory, null);
        return view;
    }
}