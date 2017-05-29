package com.daking.sports.fragment.betting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.adapter.BettingAdapter;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 18 on 2017/5/7.
 */

public class FootballFragment extends BaseFragment{
    private BettingAdapter bettingAdapter;
    private PullToRefreshView mPullToRefreshView;
    private ListView lv_betting;
    private  String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, null);
        if (null != getArguments().getString(SportsKey.TYPE)) {
            type = getArguments().getString(SportsKey.TYPE);
        }
        LogUtil.e("===FootballFragment==type======="+type);
        getFootball(type);

        lv_betting= (ListView) view.findViewById(R.id.lv_betting);
        bettingAdapter=new BettingAdapter(getActivity());
        lv_betting.setAdapter(bettingAdapter);
        bettingAdapter.notifyDataSetChanged();

        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(true);

                    }
                }, 1000);
            }
        });


        return view;
    }

    private void getFootball(String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "mlist")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(),SportsKey.UID,"0"))
                .add(SportsKey.BALL, "football")
                .add(SportsKey.TYPE, type)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.LOGIN)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String message = response.body().string();
                LogUtil.e("=======getFootball==============="+message);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
