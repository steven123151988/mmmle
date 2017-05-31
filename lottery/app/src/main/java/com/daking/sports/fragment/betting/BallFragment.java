package com.daking.sports.fragment.betting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.adapter.BettingAdapter;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.FootballGQRsp;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by 18 on 2017/5/7. 球赛列表面页
 */

public class BallFragment extends BaseFragment {
    private BettingAdapter bettingAdapter;
    private PullToRefreshView mPullToRefreshView;
    private ListView lv_betting;
    private String ball, type;
    private FootballGQRsp footballGQRsp;
    private Gson gson = new Gson();
    private Timer timer;
    private ImageView iv_system_error;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, null);
        iv_system_error=(ImageView) view.findViewById(R.id.iv_system_error);
        iv_system_error.setVisibility(View.GONE);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setVisibility(view.VISIBLE);

        if (null != getArguments().getString(SportsKey.BALL)) {
            ball = getArguments().getString(SportsKey.BALL);
        }
        if (null != getArguments().getString(SportsKey.TYPE)) {
            type = getArguments().getString(SportsKey.TYPE);
        }

        getballmsg(ball, type);

        //每隔着5秒刷新一次
        timer = new Timer();
        timer.schedule(new MyTask(), 0, 10000);
        LogUtil.e("===BallFragment==type=======" + ball + type);
        lv_betting = (ListView) view.findViewById(R.id.lv_betting);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        getballmsg(ball, type);
                    }
                }, 1000);
            }
        });
        return view;
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            getballmsg(ball, type);
        }
    }

    private void getballmsg(String ball, String type) {
        if (null == ball || null == type) {
            return;
        }
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "mlist")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.BALL, ball)
                .add(SportsKey.TYPE, type)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.MATCH_LIST)
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
                LogUtil.e("========getballmsg==========" + message);
                try {
                    footballGQRsp = gson.fromJson(message, FootballGQRsp.class);
                    if (null==footballGQRsp){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch(footballGQRsp.getCode()){
                                case 0:
                                    bettingAdapter = new BettingAdapter(getActivity(), footballGQRsp.getIfo());
                                    lv_betting.setAdapter(bettingAdapter);
                                    bettingAdapter.notifyDataSetChanged();
                                    break;
                                case 9:
                                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                case 11:
                                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                case 7:
                                    ToastUtil.show(getActivity(),footballGQRsp.getMsg());
                                    break;
                                case 8:
                                    ToastUtil.show(getActivity(),footballGQRsp.getMsg());
                                    break;
                                case 1000:
                                    mPullToRefreshView.setVisibility(View.GONE);
                                    iv_system_error.setVisibility(View.VISIBLE);
                                    break;
                                case 1001:
                                    mPullToRefreshView.setVisibility(View.GONE);
                                    iv_system_error.setVisibility(View.VISIBLE);
                                    break;
                                case 1002:
                                    mPullToRefreshView.setVisibility(View.GONE);
                                    iv_system_error.setVisibility(View.VISIBLE);
                                    break;
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
