package com.daking.sports.fragment.bettingrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.adapter.AccountHistoryAdapter;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.AccountHistoryRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/11. 账户历史
 */

public class AccountHistoryFrgment extends BaseFragment {
    private ListView lv_history;
    private String message;
    private AccountHistoryAdapter accountHistoryAdapter;
    private AccountHistoryRsp accountHistoryRsp;
    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounthistory, null);
        lv_history = (ListView) view.findViewById(R.id.lv_history);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getHistory();
//            }
//        }).start();
        getHistory();
        return view;
    }

    private void getHistory() {
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, "bet_his")
//                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.BET_HIS)
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
//                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
//                        }
//                    });
//                }
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
//                                LogUtil.e("====getHistory===========" + message);
//                                accountHistoryRsp = gson.fromJson(message, AccountHistoryRsp.class);
//                                if (null == accountHistoryRsp) {
//                                    ShowDialogUtil.showSystemFail(getActivity());
//                                    return;
//                                }
//                                switch (accountHistoryRsp.getCode()) {
//                                    case SportsKey.TYPE_ZERO:
//                                        accountHistoryAdapter = new AccountHistoryAdapter(getActivity(), accountHistoryRsp);
//                                        lv_history.setAdapter(accountHistoryAdapter);
//                                        accountHistoryAdapter.notifyDataSetChanged();
//                                        break;
//                                    case SportsKey.TYPE_NINETEEN:
//                                        //没记录
//                                        ToastUtil.show(getActivity(), "暂时没有记录！");
//                                        break;
//                                }
//
//
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
        HttpRequest.getInstance().getBetHistory(AccountHistoryFrgment.this, uid, new HttpCallback<AccountHistoryRsp>() {
            @Override
            public void onSuccess(AccountHistoryRsp data) {
                accountHistoryAdapter = new AccountHistoryAdapter(getActivity(), accountHistoryRsp);
                lv_history.setAdapter(accountHistoryAdapter);
                accountHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                if (msgCode.equals(SportsKey.TYPE_NINETEEN)) {
                    //没记录
                    ToastUtil.show(getActivity(), "暂时没有记录！");
                } else {
                    ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), errorMsg);
                }

            }
        });
    }


}
