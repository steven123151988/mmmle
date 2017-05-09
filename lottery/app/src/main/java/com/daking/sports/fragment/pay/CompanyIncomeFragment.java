package com.daking.sports.fragment.pay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;

/**
 * Created by 18 on 2017/5/7. 公司入款
 */

public class CompanyIncomeFragment extends BaseFragment implements View.OnClickListener{
    private EditText et_money;
    private String money;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_companyincome, null);
        view.findViewById(R.id.tv_check_bank).setOnClickListener(this);
        view.findViewById(R.id.tv_use_companyincome).setOnClickListener(this);
        view.findViewById(R.id.rl_bank).setOnClickListener(this);
        view.findViewById(R.id.rl_pay_time).setOnClickListener(this);
        view.findViewById(R.id.rl_type).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        et_money=(EditText) view.findViewById(R.id.et_money);
        return view;
    }

    @Override
    public void onClick(View v) {
         switch(v.getId()) {
             case R.id.tv_check_bank:
             break;
             case R.id.tv_use_companyincome:
                 break;
             case R.id.rl_pay_time:
                 break;
             case R.id.rl_bank:
                 break;
             case R.id.rl_type:
                 break;
             case R.id.btn_confirm_pay:
                 //入款金额
                 money=et_money.getText().toString().replace(" ","");


                 break;


        }

    }
}
