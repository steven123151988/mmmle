package com.daking.sports.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsUrl;
import com.daking.sports.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *   登陆页
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText et_account, et_psw;
    private String account, psw;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_account = (EditText) findViewById(R.id.et_account);
        account = et_account.getText().toString().replace(" ","");
        et_psw = (EditText) findViewById(R.id.et_psw);
        psw = et_psw.getText().toString().replace(" ","");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        findViewById(R.id.btn_forgetPsw).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                startActivity(new Intent(getApplicationContext(), RegistActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_forgetPsw:

                break;


        }
    }

    private void login() {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", account)
                .add("password", psw)
                .add("fnName", "reg")
                .add("langx", "zh-cn")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsUrl.BASE_URL+ SportsUrl.LOGIN)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e( "onFailure=" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e( "onResponse=" + response.body().string());

            }
        });
    }


}
