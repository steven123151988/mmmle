package com.daking.sports.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *   注册面页
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
    private TextView tv_center;

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
        tv_center=(TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.regist));
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
        account =et_account.getText().toString().replace(" ","");
        psw=et_psw.getText().toString().replace(" ","");
        psw2=et_psw2.getText().toString().replace(" ","");
        name=et_name.getText().toString().replace(" ","");
        money_psw=et_money_psw.getText().toString().replace(" ","");
        email=et_email.getText().toString().replace(" ","");
        RequestBody requestBody = new FormBody.Builder()
                .add("fnName", "reg")
                .add("keys", "add")
                .add("reg", "2")
                .add("intr", "add")  //代理账号
                .add("username", account)//账号
                .add("password", psw)//密码
                .add("currency", "RMB")  //首选货币
                .add("alias", name)  //真实姓名
                .add("question", "") //密码提示问题
                .add("answer", "")//答案
                .add("Sex", "1") //性别
                .add("drpAuthCodea", "")
                .add("drpAuthCodeb", "")
                .add("drpAuthCodec", "")
                .add("drpAuthCoded", "")
                .add("drpAuthCodee", "")
                .add("drpAuthCodef", "")
                .add("year11", "")
                .add("maoth11", "")
                .add("day11", "")
                .add("contory", "中国")
                .add("city", "")
                .add("know_site", "")
                .add("Checkbox", "")
                .build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL+ SportsAPI.REGIST)
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
