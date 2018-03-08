package com.daking.sports.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.activity.mine.BettingRecordsActivity;
import com.daking.sports.activity.mine.DepositRecordsActivity;
import com.daking.sports.activity.mine.PayActivity;
import com.daking.sports.activity.mine.PswManagerActivity;
import com.daking.sports.activity.mine.TakeOutMoneyActivity;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我的-个人中心面页
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private ServiceFragment serviceFragment;
    private TextView tv_name, tv_account_money;
    private FirstFragment firstFragment;
    private LoginRsp loginRsp;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, null);
        view.findViewById(R.id.rl_1).setOnClickListener(this);
        view.findViewById(R.id.rl_2).setOnClickListener(this);
        view.findViewById(R.id.rl_3).setOnClickListener(this);
        view.findViewById(R.id.rl_4).setOnClickListener(this);
        view.findViewById(R.id.rl_5).setOnClickListener(this);
        view.findViewById(R.id.rl_6).setOnClickListener(this);
        view.findViewById(R.id.rl_7).setOnClickListener(this);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_account_money = (TextView) view.findViewById(R.id.tv_account_money);
        tv_name.setText(SharePreferencesUtil.getString(getActivity(), SportsKey.USER_NAME, ""));
        String money = SharePreferencesUtil.getString(getActivity(), SportsKey.ACCOUNT_MONEY, "0");
        tv_account_money.setText("账户余额：" + money);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineFragment");
    }

    @Override
    public void onClick(View v) {
        if (null == getActivity())
            return;
        switch (v.getId()) {
            case R.id.rl_1:
                //充值
                startActivity(new Intent(getActivity(), PayActivity.class));
                break;
            case R.id.rl_2:
                //提款
                startActivity(new Intent(getActivity(), TakeOutMoneyActivity.class));
                break;
            case R.id.rl_3:
                //投注记录
                startActivity(new Intent(getActivity(), BettingRecordsActivity.class));
                break;
            case R.id.rl_4:
                //账户明细
                startActivity(new Intent(getActivity(), DepositRecordsActivity.class));
                break;
            case R.id.rl_5:
                //密码管理
                startActivity(new Intent(getActivity(), PswManagerActivity.class));
                break;
            case R.id.rl_6:
                //客服
                if (null == serviceFragment) {
                    serviceFragment = new ServiceFragment();
                }
                ((MainActivity) getActivity()).showFragmentViews(SportsKey.TYPE_SIX, serviceFragment);
                break;
            case R.id.rl_7:
                //退出登录
                loginout();
                break;
        }
    }

    /**
     * 退出登陆
     */
    private void loginout() {
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.FNNAME, SportsKey.LOGIN_OUT)
//                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, ""))
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.LOGIN_OUT)
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
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                message = response.body().string();
//                //到主线程上更新UI
//                if (null != getActivity()) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                LogUtil.e("===============loginout=========" + message);
//                                Gson gson = new Gson();
//                                loginRsp = gson.fromJson(message, LoginRsp.class);
//                                if (null == loginRsp) {
//                                    ShowDialogUtil.showSystemFail(getActivity());
//                                    return;
//                                }
//                                switch (loginRsp.getCode()) {
//                                    case SportsKey.TYPE_ZERO:
//                                        if (null == firstFragment) {
//                                            firstFragment = new FirstFragment();
//                                        }
//                                        SharePreferencesUtil.addString(getActivity(), SportsKey.UID, "0");
//                                        ((MainActivity) getActivity()).showFragmentViews(SportsKey.TYPE_ONE, firstFragment);
//                                        ToastUtil.show(getActivity(), loginRsp.getMsg());
//                                        break;
//                                    case SportsKey.TYPE_SIX:
//                                        ToastUtil.show(getActivity(), loginRsp.getMsg());
//                                        break;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                ShowDialogUtil.showSystemFail(getActivity());
//                            } finally {
//                            }
//
//                        }
//                    });
//
//                }
//
//            }
//        });

        if (null == getActivity()) return;
        String uid = SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "");
        HttpRequest.getInstance().loginOut(MineFragment.this, uid, new HttpCallback<LoginRsp>() {
            @Override
            public void onSuccess(LoginRsp data) {
                if (null == firstFragment) {
                    firstFragment = new FirstFragment();
                }
                SharePreferencesUtil.addString(getActivity(), SportsKey.UID, "0");
                ((MainActivity) getActivity()).showFragmentViews(SportsKey.TYPE_ONE, firstFragment);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ShowDialogUtil.showFailDialog(getActivity(), getActivity().getString(R.string.sorry), errorMsg);

            }
        });
    }
}

