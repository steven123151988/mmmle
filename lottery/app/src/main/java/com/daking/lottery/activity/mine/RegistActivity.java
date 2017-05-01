package com.daking.lottery.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseActivity;

/**
 * 注册页
 */
public class RegistActivity extends BaseActivity  implements View.OnClickListener{
    private EditText et_agent;
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
        et_agent=(EditText)findViewById(R.id.et_agent);
        et_account=(EditText)findViewById(R.id.et_account);
        et_psw=(EditText)findViewById(R.id.et_psw);
        et_psw2=(EditText)findViewById(R.id.et_psw2);
        et_name=(EditText)findViewById(R.id.et_name);
        et_money_psw=(EditText)findViewById(R.id.et_money_psw);
        et_email=(EditText)findViewById(R.id.et_email);

        agent=et_agent.getText().toString();
        account=et_account.getText().toString();
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

                break;

        }
    }
}
