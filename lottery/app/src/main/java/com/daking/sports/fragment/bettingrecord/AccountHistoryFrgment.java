package com.daking.sports.fragment.bettingrecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/11. 账户历史面页
 */

public class AccountHistoryFrgment extends BaseFragment {
    private ListView lv_history;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounthistory, null);
        lv_history = (ListView) view.findViewById(R.id.lv_history);
        getHistory();
        return view;
    }

    private void getHistory() {

        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "bet_his")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.DATE_START, "2017-06-01")
                .add(SportsKey.DATE_END, "2017-06-08")
                .add(SportsKey.PAGE, "1")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.BET_HISTORY)
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
                               LogUtil.e("====getHistory==========="+message);
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
