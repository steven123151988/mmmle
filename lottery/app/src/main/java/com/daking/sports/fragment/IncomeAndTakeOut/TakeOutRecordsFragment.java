package com.daking.sports.fragment.IncomeAndTakeOut;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;

/**
 * Created by steven on 2017/6/13.  取款记录
 */

public class TakeOutRecordsFragment extends BaseFragment {
    private  View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_takeout_records, null);
        return view;
    }
}
