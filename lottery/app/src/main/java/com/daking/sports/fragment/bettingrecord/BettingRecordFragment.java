package com.daking.sports.fragment.bettingrecord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.sports.R;
import com.daking.sports.activity.betting.BettingDetailActivity;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.BettingRecordRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.view.explosionfield.ExplosionField;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18steven on 2017/6/12. 球类的下注记录
 */

public class BettingRecordFragment extends BaseFragment {
    private String ball;
    private String message;
    private BettingRecordRsp bettingRecordRsp;
    private Gson gson=new Gson();
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, null);
        if (null != getArguments().getString(SportsKey.BALL)) {
            ball = getArguments().getString(SportsKey.BALL);
        }
        LogUtil.e("=========ball=====" + ball);
        getHistory(ball);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }

    private void getHistory(String ball) {

        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "betlist")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.BALL, ball)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.BET_BETTING)
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
                    LogUtil.e("====getHistory===========" + message);
                    bettingRecordRsp=gson.fromJson(message,BettingRecordRsp.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                switch (bettingRecordRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
//                                    bettingAdapter = new BettingAdapter(getActivity(), ballGQRsp.getIfo(), ball);
//                                    lv_betting.setAdapter(bettingAdapter);
//                                    bettingAdapter.notifyDataSetChanged();
                                        break;
                                    case SportsKey.TYPE_NINE:
                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                                        break;
                                    case SportsKey.TYPE_NINETEEN:
                                      ShowDialogUtil.showFailDialog(getActivity(),getString(R.string.sorry),bettingRecordRsp.getMsg());
                                        if (null == handler) {
                                            handler = new Handler();
                                        }
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                               ShowDialogUtil.dismissDialogs();
                                            }
                                        }, 2000);
                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(),getString(R.string.sorry),bettingRecordRsp.getMsg());
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
