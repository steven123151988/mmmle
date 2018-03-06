package com.daking.sports.activity.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
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
import com.daking.sports.util.CustomVideoView;
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
 * 登陆页
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText et_account, et_psw;
    private String account, psw, message;
    private LoginRsp LoginRsp;
    private TextView tv_center;
    private Gson gson;
    private Handler handler;
    //创建播放视频的控件对象
    private CustomVideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化其他view
        initView();
    }

    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_psw = (EditText) findViewById(R.id.et_psw);
        //不让用户按回车键
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


    private void initVideoview() {
        //加载视频资源控件
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径

        videoview.setVideoURI(Uri.parse("android.resource://com.daking.sports/" + R.raw.loginback));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(mContext, RegistActivity.class));
                break;
            case R.id.btn_login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }).start();
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
                    .add(SportsKey.FNNAME, SportsKey.LOGIN)
                    .add(SportsKey.LANGUAGE, SportsKey.ZH_CN)
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
                                LogUtil.e("=======login===onResponse===" + message);
                                gson = new Gson();
                                LoginRsp = gson.fromJson(message, LoginRsp.class);
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
                                    ShowDialogUtil.showSuccessDialog(mContext, getString(R.string.loginsuccss), LoginRsp.getMsg());
                                    //延迟5秒关闭
                                    handler = new Handler();
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
                                    ShowDialogUtil.showFailDialog(mContext, getString(R.string.loginerr), LoginRsp.getMsg());
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化音频
        initVideoview();
    }

    @Override
    public void onBackPressed() {
        if (!SharePreferencesUtil.getString(mContext, SportsKey.UID, "0").equals("0")) {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != videoview) {
            videoview.stopPlayback();
        }
        ShowDialogUtil.dismissDialogs();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
