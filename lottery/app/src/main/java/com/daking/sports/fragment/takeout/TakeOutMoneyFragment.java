package com.daking.sports.fragment.takeout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.util.ToastUtil;

/**
 * Created by 18 on 2017/5/10.  在线提款
 */

public class TakeOutMoneyFragment extends BaseFragment  implements View.OnClickListener{
    private EditText et_takeoutmoney_psw,et_money;
    private String takeoutmoney_psw,money;
    private TextView tv_tabkeout_bank,tv_takeout_num,tv_takeout_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeoutmoney, null);
        et_takeoutmoney_psw=(EditText)view.findViewById(R.id.et_takeoutmoney_psw);
        et_money=(EditText)view.findViewById(R.id.et_money);
        tv_tabkeout_bank= (TextView) view.findViewById(R.id.tv_tabkeout_bank);//银行
        tv_takeout_num= (TextView) view.findViewById(R.id.tv_takeout_num);//银行号码
        tv_takeout_name= (TextView) view.findViewById(R.id.tv_takeout_name);//账户名
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_confirm_pay:
                money=et_money.getText().toString().replace(" ","");//提款金额
                takeoutmoney_psw=et_takeoutmoney_psw.getText().toString().replace(" " ,""); //银行看账号
                if (TextUtils.isEmpty(money)||TextUtils.isEmpty(takeoutmoney_psw)){
                    ToastUtil.show(getActivity(),getResources().getString(R.string.accountisempty));
                }else{


                    //等待接口
                }

                break;

        }
    }
}
