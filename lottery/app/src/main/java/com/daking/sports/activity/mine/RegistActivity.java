package com.daking.sports.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
 * 注册页
 */
public class RegistActivity extends BaseActivity  implements View.OnClickListener{
    private EditText et_account;
    private EditText et_psw;
    private EditText et_psw2;
    private EditText et_name;
    private EditText et_money_psw;
    private EditText et_email;
    private String agent;
    private String account;
    private String psw;
    private String psw2;
    private String name;
    private String money_psw;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        et_account=(EditText)findViewById(R.id.et_account);
        et_psw=(EditText)findViewById(R.id.et_psw);
        et_psw2=(EditText)findViewById(R.id.et_psw2);
        et_name=(EditText)findViewById(R.id.et_name);
        et_money_psw=(EditText)findViewById(R.id.et_money_psw);
        et_email=(EditText)findViewById(R.id.et_email);

        account =et_account.getText().toString();
        psw=et_psw.getText().toString();
        psw2=et_psw2.getText().toString();
        name=et_name.getText().toString();
        money_psw=et_money_psw.getText().toString();
        email=et_email.getText().toString();

        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch(v.getId()) {
            case R.id.btn_register:
                regist();
                break;

        }
    }

    private void regist() {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", account)
                .add("password", "long")
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsUrl.BASE_URL+ SportsUrl.REGIST)
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
