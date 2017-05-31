package com.daking.sports.activity.mine;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
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
 * 修改密码管理
 */
public class PswManagerActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_center;
    private ImageView iv_back;
    private EditText et_psw1,et_psw2,et_psw3,et_money_psw1,et_money_psw2,et_money_psw3;
    private String   psw1,psw2,psw3,money_psw1,money_psw2,money_psw3;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifi_psw);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.pc_paw_manager));
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        et_psw1=(EditText) findViewById(R.id.et_psw1);
        et_psw2=(EditText) findViewById(R.id.et_psw2);
        et_psw3=(EditText) findViewById(R.id.et_psw3);
        et_money_psw1=(EditText) findViewById(R.id.et_money_psw1);
        et_money_psw2=(EditText) findViewById(R.id.et_money_psw2);
        et_money_psw3=(EditText) findViewById(R.id.et_money_psw3);
        btn_confirm=(Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case  R.id.btn_confirm:
                psw1=et_psw1.getText().toString().replace(" ","");
                psw2=et_psw2.getText().toString().replace(" ","");
                psw3=et_psw3.getText().toString().replace(" ","");
                if (TextUtils.isEmpty(psw1)||TextUtils.isEmpty(psw2)||TextUtils.isEmpty(psw3)){
                    ToastUtil.show(mContext,getResources().getString(R.string.pswisempty));
                }else{
                        RequestBody requestBody = new FormBody.Builder()
                                .add("username", "")
                                .add("password", "")
                                .add("fnName", "lg")
                                .add("langx", "zh-cn")
                                .build();

                        final okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(SportsAPI.BASE_URL+ SportsAPI.MODIFY_PSW)
                                .post(requestBody)
                                .build();

                        OkHttpClient okHttpClient = new OkHttpClient();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                LogUtil.e("============="+response.body().string());
//                                Gson gson=new Gson();
//                                LoginRsp =gson.fromJson(response.body().string(), LoginRsp.class);
//                                SharePreferencesUtil.addString(mContext, SportsKey.UID,LoginRsp.getIfo().getUid());
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //展示登录消息
//                                        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
//                                                .setTitleText(getString(R.string.loginsuccss))
//                                                .setContentText(LoginRsp.getIfo().getMsg())
//                                                .write();
//
//                                        //延迟3秒关闭
//                                        Handler handler=new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                setResult(RESULT_OK);
//                                                finish();
//                                            }
//                                        },3000);
//                                    }
//                                });
                            }
                        });
                    }



                break;
            case  R.id.btn_money_confirm:
                money_psw1=et_money_psw1.getText().toString().replace(" ","");
                money_psw2=et_money_psw2.getText().toString().replace(" ","");
                money_psw3=et_money_psw3.getText().toString().replace(" ","");
                if (TextUtils.isEmpty(money_psw1)||TextUtils.isEmpty(money_psw2)||TextUtils.isEmpty(money_psw3)){
                    ToastUtil.show(mContext,getResources().getString(R.string.pswisempty));
                }else{
                      //等待接口
                }

                break;

        }

    }
}
