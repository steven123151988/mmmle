package com.daking.sports.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.activity.mine.BettingRecordsActivity;
import com.daking.sports.activity.mine.DepositRecordsActivity;
import com.daking.sports.activity.mine.PayActivity;
import com.daking.sports.activity.mine.PswManagerActivity;
import com.daking.sports.activity.mine.TakeOutMoneyActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsId;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private TextView tv_name;


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
        tv_name.setText(SharePreferencesUtil.getString(getActivity(), SportsKey.USER_NAME, ""));
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
                ((MainActivity) getActivity()).showFragmentViews(SportsId.TYPE_SIX, serviceFragment);
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
        LogUtil.e("===============message=========" +  SharePreferencesUtil.getString(getActivity(), SportsKey.UID, ""));
        RequestBody requestBody = new FormBody.Builder()
                .add("fnName", "lgout")
                .add("uid", SharePreferencesUtil.getString(getActivity(), SportsKey.UID, ""))
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.LOGIN_OUT)
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
                LogUtil.e("===============message=========" + message);

            }
        });
    }
}
