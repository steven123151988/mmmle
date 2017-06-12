package com.daking.sports.fragment.betting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.daking.sports.json.BallGQRsp;
import com.daking.sports.util.AbsListViewCompat;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
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
    private String ball, balltype;
    private BallGQRsp ballGQRsp;
    private Gson gson = new Gson();
    private Timer timer;
    private ImageView iv_system_error;
    private AbsListViewCompat.OnScrollCallback onScrollCallback;
    private int listview_position = 0;
    private String message;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, null);
        iv_system_error = (ImageView) view.findViewById(R.id.iv_system_error);
        iv_system_error.setVisibility(View.GONE);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setVisibility(view.VISIBLE);

        if (null != getArguments().getString(SportsKey.BALL)) {
            ball = getArguments().getString(SportsKey.BALL);
        }
        if (null != getArguments().getString(SportsKey.TYPE)) {
            balltype = getArguments().getString(SportsKey.TYPE);
        }
        LogUtil.e("===BallFragment==type=======" + ball + balltype);
        lv_betting = (ListView) view.findViewById(R.id.lv_betting);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        getballmsg(ball, balltype);
                    }
                }, 1000);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //每隔着5秒刷新一次
        if (null == timer) {
            timer = new Timer();
        }
        timer.schedule(new MyTask(), 0, 10000);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerCancle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerCancle();
        if (null!=handler){
            handler.removeCallbacksAndMessages(null);
        }

    }

    private void timerCancle() {
        if (null != timer) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            getballmsg(ball, balltype);
        }
    }


    /**
     * 获取球类列表信息
     *
     * @param ball
     * @param type
     */
    private void getballmsg(final String ball, String type) {
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.showSystemFail(getActivity());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LogUtil.e("=====getballmsg====="+message);
                                ballGQRsp = gson.fromJson(message, BallGQRsp.class);
                                if (null == ballGQRsp) {
                                    mPullToRefreshView.setVisibility(View.GONE);
                                    iv_system_error.setVisibility(View.VISIBLE);
                                    iv_system_error.setImageResource(R.drawable.konglong1);
                                    return;
                                }
                                switch (ballGQRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        bettingAdapter = new BettingAdapter(getActivity(), ballGQRsp.getIfo(), ball);
                                        lv_betting.setAdapter(bettingAdapter);
                                        bettingAdapter.notifyDataSetChanged();
                                        lv_betting.setSelection(listview_position);
                                        AbsListViewCompat absListViewCompat = new AbsListViewCompat();
                                        absListViewCompat.setScrollView(lv_betting);
                                        onScrollCallback = new AbsListViewCompat.OnScrollCallback() {
                                            @Override
                                            public void onScrollChanged(int state, int direction, int position) {
                                                listview_position = position;
                                            }
                                        };
                                        absListViewCompat.setOnScrollCallback(onScrollCallback);
                                        break;
                                    case SportsKey.TYPE_NINE:
                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                        break;
                                    case SportsKey.TYPE_ELEVEN:
                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                        break;
                                    case SportsKey.TYPE_SEVEN:
                                        mPullToRefreshView.setVisibility(View.GONE);
                                        iv_system_error.setVisibility(View.VISIBLE);
                                        iv_system_error.setImageResource(R.drawable.konglong1);
                                        break;
                                    case SportsKey.TYPE_EIGHT:
                                        mPullToRefreshView.setVisibility(View.GONE);
                                        iv_system_error.setImageResource(R.drawable.konglong4);
                                        iv_system_error.setVisibility(View.VISIBLE);
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), ballGQRsp.getMsg());
                                        //延迟2秒关闭
                                        if (null == handler) {
                                            handler = new Handler();
                                        }
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ShowDialogUtil.dismissDialogs();
                                            }
                                        }, 2500);
                                        break;
                                    case SportsKey.TYPE_1000:
                                        mPullToRefreshView.setVisibility(View.GONE);
                                        iv_system_error.setImageResource(R.mipmap.system_errors);
                                        iv_system_error.setVisibility(View.VISIBLE);
                                        break;
                                    case SportsKey.TYPE_1001:
                                        mPullToRefreshView.setVisibility(View.GONE);
                                        iv_system_error.setBackground(getResources().getDrawable(R.mipmap.system_errors));
                                        iv_system_error.setVisibility(View.VISIBLE);
                                        break;
                                    case SportsKey.TYPE_1002:
                                        mPullToRefreshView.setVisibility(View.GONE);
                                        iv_system_error.setBackground(getResources().getDrawable(R.mipmap.system_errors));
                                        iv_system_error.setVisibility(View.VISIBLE);
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowDialogUtil.showSystemFail(getActivity());
                            } finally {
                            }
                        }
                    });
                }
            }
        });
    }


}
