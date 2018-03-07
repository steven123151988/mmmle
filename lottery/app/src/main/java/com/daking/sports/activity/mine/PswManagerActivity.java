package com.daking.sports.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.api.HttpCallback;
import com.daking.sports.api.HttpRequest;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
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
 * 修改密码管理
 */
public class PswManagerActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_center;
    private ImageView iv_back;
    private EditText et_psw1, et_psw2, et_psw3, et_money_psw1, et_money_psw2, et_money_psw3;
    private String psw1, psw2, psw3, money_psw1, money_psw2, money_psw3;
    private Button btn_confirm, btn_money_confirm;
    private String message;
    private LoginRsp loginRsp;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifi_psw);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.pc_paw_manager));
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        et_psw1 = (EditText) findViewById(R.id.et_psw1);
        et_psw2 = (EditText) findViewById(R.id.et_psw2);
        et_psw3 = (EditText) findViewById(R.id.et_psw3);
        et_money_psw1 = (EditText) findViewById(R.id.et_money_psw1);
        et_money_psw2 = (EditText) findViewById(R.id.et_money_psw2);
        et_money_psw3 = (EditText) findViewById(R.id.et_money_psw3);
        AddEdiTextWatchListenerUtil.addTextWatchListener(et_money_psw1, 4);
        AddEdiTextWatchListenerUtil.addTextWatchListener(et_money_psw2, 4);
        AddEdiTextWatchListenerUtil.addTextWatchListener(et_money_psw3, 4);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        btn_money_confirm = fuck(R.id.btn_money_confirm);
        btn_money_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confirm:
                psw1 = et_psw1.getText().toString().replace(" ", "");
                psw2 = et_psw2.getText().toString().replace(" ", "");
                psw3 = et_psw3.getText().toString().replace(" ", "");
                changePsw(psw1, psw2, psw3, "account");
                break;
            case R.id.btn_money_confirm:
                money_psw1 = et_money_psw1.getText().toString().replace(" ", "");
                money_psw2 = et_money_psw2.getText().toString().replace(" ", "");
                money_psw3 = et_money_psw3.getText().toString().replace(" ", "");
                changePsw(money_psw1, money_psw2, money_psw3, "withdraw");
                break;
        }

    }

    private void changePsw(String psw1, String psw2, String psw3, String type) {
        if (TextUtils.isEmpty(psw1) || TextUtils.isEmpty(psw2) || TextUtils.isEmpty(psw3)) {
            ToastUtil.show(mContext, getResources().getString(R.string.pswisempty));
            return;
        }
        if (!psw2.equals(psw3)) {
            ToastUtil.show(mContext, getResources().getString(R.string.account_not_same));
            return;
        }
//        RequestBody requestBody = new FormBody.Builder()
//                .add(SportsKey.UID, SharePreferencesUtil.getString(mContext, SportsKey.UID, "0"))
//                .add(SportsKey.FNNAME, "chg_pwd")
//                .add(SportsKey.OLD_PWD, psw1)
//                .add(SportsKey.BEW_PWD, psw3)
//                .add(SportsKey.TYPE, type)
//                .build();
//
//        final okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(SportsAPI.BASE_URL + SportsAPI.CHANGE_PWD)
//                .post(requestBody)
//                .build();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                message = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            LogUtil.e("=============" + message);
//                            Gson gson = new Gson();
//                            loginRsp = gson.fromJson(message, LoginRsp.class);
//                            if (null == loginRsp) {
//                                ShowDialogUtil.showSystemFail(mContext);
//                                return;
//                            }
//                            switch (loginRsp.getCode()) {
//                                case SportsKey.TYPE_ZERO:
//                                    ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.change_success), loginRsp.getMsg());
//                                    if (null == handler) {
//                                        handler = new Handler();
//                                    }
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            ShowDialogUtil.dismissDialogs();
//                                            startActivity(new Intent(mContext, LoginActivity.class));
//                                            finish();
//
//                                        }
//                                    }, 2000);
//                                    break;
//
//                                default:
//                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), loginRsp.getMsg());
//                                    break;
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ShowDialogUtil.showSystemFail(mContext);
//                        } finally {
//
//                        }
//                    }
//                });
//            }
//        });
        String uid = SharePreferencesUtil.getString(mContext, SportsKey.UID, "0");
        HttpRequest.getInstance().changePsw(PswManagerActivity.this, uid, psw1, psw3, type, new HttpCallback<LoginRsp>() {
            @Override
            public void onSuccess(LoginRsp data) {
                ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.change_success), loginRsp.getMsg());
                if (null == handler) {
                    handler = new Handler();
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.dismissDialogs();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();

                    }
                }, 2000);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), errorMsg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
