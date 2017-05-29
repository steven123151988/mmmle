package com.daking.sports.fragment.betting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsKey;
import com.daking.sports.util.LogUtil;

/**
 * Created by 18 on 2017/5/7.
 */

public class BasketballFragment extends BaseFragment {
    private  String type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basketball, null);

        if (null != getArguments().getString(SportsKey.TYPE)) {
            type = getArguments().getString(SportsKey.TYPE);
        }
        LogUtil.e("===FootballFragment==type======="+type);


        return view;
    }



}
