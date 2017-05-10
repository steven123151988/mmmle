package com.daking.sports.fragment.takeout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;

/**
 * Created by 18 on 2017/5/10.  在线提款
 */

public class TakeOutMoneyFragment extends BaseFragment  implements View.OnClickListener{
    private EditText et_banknum,et_money;
    private String banknum,money;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeoutmoney, null);
        et_banknum=(EditText)view.findViewById(R.id.et_banknum);
        et_money=(EditText)view.findViewById(R.id.et_money);

        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        view.findViewById(R.id.rl_bank).setOnClickListener(this);
          return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.rl_bank:   //选择银行卡

                break;
            case R.id.btn_confirm_pay:
                money=et_money.getText().toString().replace(" ","");//提款金额
                banknum=et_banknum.getText().toString().replace(" " ,""); //银行看账号
                //等待接口
                break;

        }
    }
}
