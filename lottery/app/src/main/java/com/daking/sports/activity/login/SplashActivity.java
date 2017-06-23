package com.daking.sports.activity.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.ConfigRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.NetUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;



/**
 * Created by 18 on 2017/5/28.  启动页
 */

public class SplashActivity extends BaseActivity {
    private int sdk_version = Build.VERSION.SDK_INT;  // 进入之前获取手机的SDK版本号
    private String message;
    private ConfigRsp configRsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //先判断网络情况,可以的话走下去，不可以的话提示网络有问题
        if (NetUtil.checkNetworkState()) {
            //只适配SDK大于16的手机
            if (sdk_version > 15) {
                //延迟5秒关闭
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initConfigIndex();
                    }
                }, 2500);
            } else {
                ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.app_support_lowest_sdk));
            }

        } else {
            ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
        }
    }

    /**
     * 请求全局变量
     */
    private void initConfigIndex() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.CONFIG)
                .add(SportsKey.HOST, "le7")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.CONFIG_INDEX)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e("===========initConfigIndex====error=====");
                        ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), getString(R.string.net_error));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtil.e("=======initConfigIndex===" + message);
                            Gson gson = new Gson();
                            configRsp = gson.fromJson(message, ConfigRsp.class);
                            //返回信息解析失败，提示系统异常、
                            if (null == configRsp) {
                                ShowDialogUtil.showSystemFail(mContext);
                                return;
                            }
                            switch (configRsp.getCode()) {
                                case SportsKey.TYPE_ZERO:
                                    if (null != configRsp.getIfo().getBase_url() && !configRsp.getIfo().getBase_url().equals("")) {
                                        SportsAPI.BASE_URL = configRsp.getIfo().getBase_url();
                                        initLogType();
                                    }
                                    break;
                                default:
                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.sorry), configRsp.getMsg());
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowDialogUtil.showSystemFail(mContext);
                        }
                    }
                });
            }
        });
    }


    /**
     * 查看登陆状态，若UID为空就要去登陆
     */
    private void initLogType() {
        if (SharePreferencesUtil.getString(mContext, SportsKey.UID, "0").equals("0")) {
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }
}
