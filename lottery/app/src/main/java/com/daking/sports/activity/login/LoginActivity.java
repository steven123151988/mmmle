package com.daking.sports.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.mine.PswManagerActivity;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsKey;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.PersonalDataRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登陆页
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText et_account, et_psw;
    private String account, psw;
    private ImageView iv_back;
    private LoginRsp LoginRsp;
    private TextView tv_center;
    private Gson gson;
    private PersonalDataRsp personalDataRsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_account = (EditText) findViewById(R.id.et_account);
        et_psw = (EditText) findViewById(R.id.et_psw);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.login));
        iv_back.setOnClickListener(this);
        findViewById(R.id.btn_forgetPsw).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!SharePreferencesUtil.getString(mContext, SportsKey.UID, "").equals("")) {
                    finish();
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(mContext, RegistActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_forgetPsw:
                startActivity(new Intent(mContext, PswManagerActivity.class));
                break;
        }
    }


    private void login() {
        account = et_account.getText().toString().replace(" ", "");
        psw = et_psw.getText().toString().replace(" ", "");
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(psw)) {
            ToastUtil.show(mContext, getResources().getString(R.string.accountisempty));
        } else {
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", account)
                    .add("password", psw)
                    .add("fnName", "lg")
                    .add("langx", "zh-cn")
                    .build();

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(SportsAPI.BASE_URL + SportsAPI.LOGIN)
                    .post(requestBody)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String message = response.body().string();
                        LogUtil.e("===============message=========" + message);
                        gson = new Gson();
                        LoginRsp = gson.fromJson(message, LoginRsp.class);
                        if (LoginRsp.getCode() == 0) {
                            SharePreferencesUtil.addString(mContext, SportsKey.UID, LoginRsp.getIfo());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //展示登录成功消息
                                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText(getString(R.string.loginsuccss))
                                            .setContentText(LoginRsp.getMsg())
                                            .show();

                                    initPersonData();

                                    //延迟5秒关闭
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 2500);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //展示失败消息
                                    new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText(getString(R.string.loginerr))
                                            .setContentText(LoginRsp.getIfo())
                                            .show();
                                }
                            });

                        }
                    } catch (Exception e) {
                        LogUtil.e("========onResponse==============" + e);
                    }


                }
            });
        }
    }


    /**
     * 获取个人信息
     */
    private void initPersonData() {

        RequestBody requestBody = new FormBody.Builder()
                .add("fnName", "MData")
                .add("uid", SharePreferencesUtil.getString(mContext, SportsKey.UID, ""))
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.GET_DATA)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    gson = new Gson();
                    String message = response.body().string();
                    LogUtil.e("===========response.body().string()===========" + message);
                    personalDataRsp = gson.fromJson(message, PersonalDataRsp.class);
                    SharePreferencesUtil.addString(mContext, SportsKey.USER_NAME, personalDataRsp.getIfo().getUserName());
                } catch (Exception e) {
                    LogUtil.e("========onResponse==============" + e);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
//        if (!SharePreferencesUtil.getString(mContext, SportsKey.UID, "").equals("")) {
//            super.onBackPressed();
//        }
        super.onBackPressed();

    }
}
