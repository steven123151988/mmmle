package com.daking.sports.fragment.IncomeAndTakeOut;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daking.sports.R;
import com.daking.sports.adapter.IncomeAdapter;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.IncomeRep;
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
 * Created by steven on 2017/6/13.  取款记录
 */

public class TakeOutRecordsFragment extends BaseFragment {
    private  View view;
    private ListView lv_income_records;
    private IncomeAdapter adapter;
    private String message;
    private Gson gson = new Gson();
    private IncomeRep incomeRep;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_takeout_records, null);
        lv_income_records = (ListView) view.findViewById(R.id.lv_income_records);
        takeOutRecords();
        return view;
    }

    private void takeOutRecords() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "capital")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.TYPE, "withdraw")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.MEM_CAPITAL_FLOW)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showSystemFail(getActivity());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LogUtil.e("====takeOutRecords===========" + message);
                                incomeRep = gson.fromJson(message, IncomeRep.class);
                                if (null == incomeRep) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (incomeRep.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        adapter = new IncomeAdapter(getActivity(), incomeRep);
                                        lv_income_records.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        break;
                                    case SportsKey.TYPE_NINETEEN:
                                        //没记录
                                        ToastUtil.show(getActivity(), "暂时没记录！");
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
