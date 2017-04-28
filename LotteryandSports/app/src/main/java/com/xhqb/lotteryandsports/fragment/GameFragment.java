package com.xhqb.lotteryandsports.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xhqb.lotteryandsports.R;
import com.xhqb.lotteryandsports.base.BaseFragment;

/**
 * Created by quchuangye on 2017/4/7.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gamefragment, null);
        return view;
    }
}
