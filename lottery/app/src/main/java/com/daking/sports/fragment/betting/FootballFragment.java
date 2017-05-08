package com.daking.sports.fragment.betting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.adapter.BettingAdapter;
import com.daking.sports.base.BaseFragment;
import com.yalantis.phoenix.PullToRefreshView;


/**
 * Created by 18 on 2017/5/7.
 */

public class FootballFragment extends BaseFragment{
    private BettingAdapter bettingAdapter;
    private PullToRefreshView mPullToRefreshView;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, null);
        listView=(ListView) view.findViewById(R.id.list_view);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        bettingAdapter=new BettingAdapter(getActivity());
                        listView.setAdapter(bettingAdapter);





                    }
                }, 1000);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}