package com.daking.sports.fragment.takeout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.mine.TakeOutMoneyActivity;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.MemOnlineRsp;
import com.daking.sports.util.AddEdiTextWatchListenerUtil;
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
 * Created by 18 on 2017/5/10.  在线提款
 */

public class TakeOutMoneyFragment extends BaseFragment implements View.OnClickListener {
    private EditText et_takeoutmoney_psw, et_money;
    private String takeoutmoney_psw, money;
    private TextView tv_tabkeout_bank, tv_takeout_num, tv_takeout_name;
    private LoginRsp loginRsp;
    private String message;
    private MemOnlineRsp memonlineRsp;
    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeoutmoney, null);
        et_takeoutmoney_psw = (EditText) view.findViewById(R.id.et_takeoutmoney_psw);
        AddEdiTextWatchListenerUtil.addTextWatchListener(et_takeoutmoney_psw, 4);
        et_money = (EditText) view.findViewById(R.id.et_money);
        tv_tabkeout_bank = (TextView) view.findViewById(R.id.tv_tabkeout_bank);//银行
        tv_takeout_num = (TextView) view.findViewById(R.id.tv_takeout_num);//银行号码
        tv_takeout_name = (TextView) view.findViewById(R.id.tv_takeout_name);//账户名
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOutMoney();
    }


    private void getOutMoney() {
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, "withdrawals")
//                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.MEM_ONLINE)
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
//                                LogUtil.e("======getOutMoney========" + message);
//                                memonlineRsp = gson.fromJson(message, MemOnlineRsp.class);
//                                if (null == memonlineRsp) {
//                                    ShowDialogUtil.showSystemFail(getActivity());
//                                    return;
//                                }
//                                switch (memonlineRsp.getCode()) {
//                                    case SportsKey.TYPE_ZERO:
//                                        if (memonlineRsp.getIfo().getBank_Account().equals("")) {
//                                            ((TakeOutMoneyActivity) getActivity()).addBankAccount();
//                                        } else {
//                                            tv_tabkeout_bank.setText(memonlineRsp.getIfo().getBank());
//                                            tv_takeout_num.setText(memonlineRsp.getIfo().getBank_Account());
//                                            tv_takeout_name.setText(memonlineRsp.getIfo().getAlias());
//                                            ((TakeOutMoneyActivity) getActivity()).getTakeOutMoneyView();
//                                        }
//                                        break;
//                                    default:
//                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), memonlineRsp.getMsg());
//                                        break;
//
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                ShowDialogUtil.showSystemFail(getActivity());
//                            }
//                        }
//                    });
//                }
//
//            }
//        });
        String uid = SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0");
        HttpRequest.getInstance().memOnline(TakeOutMoneyFragment.this, uid, new HttpCallback<MemOnlineRsp>() {
            @Override
            public void onSuccess(MemOnlineRsp data) {
                if (memonlineRsp.getIfo().getBank_Account().equals("")) {
                    ((TakeOutMoneyActivity) getActivity()).addBankAccount();
                } else {
                    tv_tabkeout_bank.setText(memonlineRsp.getIfo().getBank());
                    tv_takeout_num.setText(memonlineRsp.getIfo().getBank_Account());
                    tv_takeout_name.setText(memonlineRsp.getIfo().getAlias());
                    ((TakeOutMoneyActivity) getActivity()).getTakeOutMoneyView();
                }

            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), errorMsg);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_pay:
                takeOutMoney();
                break;
        }
    }


    /**
     * 取款
     */
    private void takeOutMoney() {
        money = et_money.getText().toString().replace(" ", "");//提款金额
        takeoutmoney_psw = et_takeoutmoney_psw.getText().toString().replace(" ", ""); //提款密码
        if (TextUtils.isEmpty(money)) {
            ToastUtil.show(getActivity(), getString(R.string.type_in_money));
            return;
        }
        if (TextUtils.isEmpty(takeoutmoney_psw)) {
            ToastUtil.show(getActivity(), getString(R.string.type_in_psw));
            return;
        }
        if (Integer.parseInt(money) < 100) {
            ToastUtil.show(getActivity(), getString(R.string.take_out_money));
            return;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "draw")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.PASSWORD, takeoutmoney_psw)
                .add(SportsKey.MONEY, money)
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.MEM_ONLINE_POST)
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
                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
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
                                LogUtil.e("========message========" + message);
                                loginRsp = gson.fromJson(message, LoginRsp.class);
                                if (null == loginRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (loginRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        ShowDialogUtil.showSuccessDialog(getActivity(), getString(R.string.sucess_congratulations), loginRsp.getMsg());
                                        et_takeoutmoney_psw.getText().clear();
                                        et_money.getText().clear();
                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), loginRsp.getMsg());
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }
}