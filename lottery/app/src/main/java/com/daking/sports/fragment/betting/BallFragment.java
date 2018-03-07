package com.daking.sports.fragment.betting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.adapter.BettingAdapter;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BallGQRsp;
import com.daking.sports.json.LotteryVersion;
import com.daking.sports.util.AbsListViewCompat;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.google.gson.Gson;

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
    private ListView lv_betting;
    private String ball, balltype;
    private BallGQRsp ballGQRsp;
    private Gson gson = new Gson();
    private Timer timer;
    private ImageView iv_system_error;
    private AbsListViewCompat.OnScrollCallback onScrollCallback;
    private int listview_position = 0;
    private String message;
    private LinearLayout ll_redline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, null);
        iv_system_error = (ImageView) view.findViewById(R.id.iv_system_error);
        iv_system_error.setVisibility(View.GONE);
        ll_redline = (LinearLayout) view.findViewById(R.id.ll_redline);
        if (null != getArguments().getString(SportsKey.BALL)) {
            ball = getArguments().getString(SportsKey.BALL);
        }
        if (null != getArguments().getString(SportsKey.TYPE)) {
            balltype = getArguments().getString(SportsKey.TYPE);
        }
        LogUtil.e("===BallFragment==type=======" + ball + balltype);
        lv_betting = (ListView) view.findViewById(R.id.lv_betting);

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
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, "mlist")
//                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
//                .add(SportsKey.BALL, ball)
//                .add(SportsKey.TYPE, type)
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.MATCH_LIST)
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (null != getActivity()) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            iv_system_error.setVisibility(View.VISIBLE);
//                            iv_system_error.setImageResource(R.drawable.konglong4);
//                            ll_redline.setVisibility(View.GONE);
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                message = response.body().string();
//                if (null != getActivity()) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                LogUtil.e("=====getballmsg=====" + message);
//                                ballGQRsp = gson.fromJson(message, BallGQRsp.class);
//                                if (null == ballGQRsp) {
//                                    iv_system_error.setVisibility(View.VISIBLE);
//                                    iv_system_error.setImageResource(R.drawable.konglong1);
//                                    return;
//                                }
//                                switch (ballGQRsp.getCode()) {
//                                    case SportsKey.TYPE_ZERO:
//                                        iv_system_error.setVisibility(View.GONE);
//                                        bettingAdapter = new BettingAdapter(getActivity(), ballGQRsp.getIfo(), ball);
//                                        lv_betting.setAdapter(bettingAdapter);
//                                        bettingAdapter.notifyDataSetChanged();
//                                        lv_betting.setSelection(listview_position);
//                                        AbsListViewCompat absListViewCompat = new AbsListViewCompat();
//                                        absListViewCompat.setScrollView(lv_betting);
//                                        onScrollCallback = new AbsListViewCompat.OnScrollCallback() {
//                                            @Override
//                                            public void onScrollChanged(int state, int direction, int position) {
//                                                listview_position = position;
//                                            }
//                                        };
//                                        absListViewCompat.setOnScrollCallback(onScrollCallback);
//                                        if (ballGQRsp.getIfo().size() > 0) {
//                                            ll_redline.setVisibility(View.VISIBLE);
//                                        } else {
//                                            ll_redline.setVisibility(View.GONE);
//                                        }
//
//                                        break;
//                                    case SportsKey.TYPE_NINE:
//                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//                                        break;
//                                    case SportsKey.TYPE_ELEVEN:
//                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//                                        break;
//                                    case SportsKey.TYPE_SEVEN:
//                                        iv_system_error.setVisibility(View.VISIBLE);
//                                        iv_system_error.setImageResource(R.drawable.konglong1);
//                                        ll_redline.setVisibility(View.GONE);
//                                        break;
//                                    case SportsKey.TYPE_EIGHT:
//                                        iv_system_error.setImageResource(R.drawable.konglong1);
//                                        iv_system_error.setVisibility(View.VISIBLE);
//                                        ll_redline.setVisibility(View.GONE);
//                                        break;
//                                    case SportsKey.TYPE_1000:
//                                        iv_system_error.setImageResource(R.drawable.konglong4);
//                                        iv_system_error.setVisibility(View.VISIBLE);
//                                        ll_redline.setVisibility(View.GONE);
//                                        break;
//                                    case SportsKey.TYPE_1001:
//                                        iv_system_error.setImageResource(R.drawable.konglong4);
//                                        iv_system_error.setVisibility(View.VISIBLE);
//                                        ll_redline.setVisibility(View.GONE);
//                                        break;
//                                    case SportsKey.TYPE_1002:
//                                        iv_system_error.setImageResource(R.drawable.konglong4);
//                                        iv_system_error.setVisibility(View.VISIBLE);
//                                        ll_redline.setVisibility(View.GONE);
//                                        break;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                ShowDialogUtil.showSystemFail(getActivity());
//                            } finally {
//                            }
//                        }
//                    });
//                }
//            }
//        });
        String uid = SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0");
        HttpRequest.getInstance().getBallMsg(BallFragment.this, uid, ball, type, new HttpCallback<BallGQRsp>() {

            @Override
            public void onSuccess(BallGQRsp data) {
                iv_system_error.setVisibility(View.GONE);
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
                if (ballGQRsp.getIfo().size() > 0) {
                    ll_redline.setVisibility(View.VISIBLE);
                } else {
                    ll_redline.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                switch (msgCode) {
                    case SportsKey.TYPE_NINE + "":
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        break;
                    case SportsKey.TYPE_ELEVEN + "":
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        break;
                    case SportsKey.TYPE_SEVEN + "":
                        iv_system_error.setVisibility(View.VISIBLE);
                        iv_system_error.setImageResource(R.drawable.konglong1);
                        ll_redline.setVisibility(View.GONE);
                        break;
                    case SportsKey.TYPE_EIGHT + "":
                        iv_system_error.setImageResource(R.drawable.konglong1);
                        iv_system_error.setVisibility(View.VISIBLE);
                        ll_redline.setVisibility(View.GONE);
                        break;
                    case SportsKey.TYPE_1000 + "":
                        iv_system_error.setImageResource(R.drawable.konglong4);
                        iv_system_error.setVisibility(View.VISIBLE);
                        ll_redline.setVisibility(View.GONE);
                        break;
                    case SportsKey.TYPE_1001 + "":
                        iv_system_error.setImageResource(R.drawable.konglong4);
                        iv_system_error.setVisibility(View.VISIBLE);
                        ll_redline.setVisibility(View.GONE);
                        break;
                    case SportsKey.TYPE_1002 + "":
                        iv_system_error.setImageResource(R.drawable.konglong4);
                        iv_system_error.setVisibility(View.VISIBLE);
                        ll_redline.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }


}
