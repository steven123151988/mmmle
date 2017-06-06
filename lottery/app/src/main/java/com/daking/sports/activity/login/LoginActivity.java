package com.daking.sports.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.activity.mine.PswManagerActivity;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsKey;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
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
    private LoginRsp LoginRsp;
    private TextView tv_center;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_account = (EditText) findViewById(R.id.et_account);
        et_psw = (EditText) findViewById(R.id.et_psw);
        //不然用户按回车键
        et_account.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        et_psw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.login));
        findViewById(R.id.btn_forgetPsw).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                    .add(SportsKey.USER_NAME, account)
                    .add(SportsKey.PASSWORD, psw)
                    .add(SportsKey.FNNAME,SportsKey.LOGIN)
                    .add(SportsKey.LANGUAGE,SportsKey.ZH_CN)
                    .build();

            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(SportsAPI.BASE_URL + SportsAPI.LOGIN)
                    .post(requestBody)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showSystemFail(mContext);
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String message = response.body().string();
                        LogUtil.e("=======login===onResponse===" + message);
                        gson = new Gson();
                        LoginRsp = gson.fromJson(message, LoginRsp.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //返回信息解析失败，提示系统异常、
                                if (null == LoginRsp) {
                                    //展示失败消息
                                    ShowDialogUtil.showSystemFail(mContext);
                                    return;
                                }
                                if (LoginRsp.getCode() == 0) {
                                    SharePreferencesUtil.addString(mContext, SportsKey.UID, LoginRsp.getIfo());
                                    SharePreferencesUtil.addString(mContext, SportsKey.USER_NAME, account);
                                    //展示成功的对话框
                                    ShowDialogUtil.showSuccessDialog(mContext,getString(R.string.loginsuccss),LoginRsp.getMsg());
                                    //延迟5秒关闭
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            ShowDialogUtil.dismissDialogs();
                                            startActivity(new Intent(mContext, MainActivity.class));
                                            finish();
                                        }
                                    }, 2500);
                                } else {
                                    //展示失败消息
                                    ShowDialogUtil.showFailDialog(mContext,getString(R.string.loginerr),LoginRsp.getMsg());
                                }
                            }
                        });

                    } catch (Exception e) {
                        LogUtil.e("========login==============" + e);
                    }
                }
            });
        }
    }






    @Override
    public void onBackPressed() {
        if (!SharePreferencesUtil.getString(mContext, SportsKey.UID, "0").equals("0")) {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }
}
