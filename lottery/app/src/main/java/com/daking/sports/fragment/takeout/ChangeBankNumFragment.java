package com.daking.sports.fragment.takeout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.util.ToastUtil;

/**
 * Created by 18 on 2017/5/10.  更改银行账号
 */

public class ChangeBankNumFragment extends BaseFragment  implements View.OnClickListener{
    private EditText et_banknum;
    private String banknum,bankname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changebanknum, null);
        et_banknum=(EditText)view.findViewById(R.id.et_banknum);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        view.findViewById(R.id.rl_bank).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm_pay).setOnClickListener(this);
        return view ;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rl_bank:   //选择银行卡

                break;
            case R.id.btn_confirm_pay:
                banknum=et_banknum.getText().toString().replace(" " ,""); //银行看账号
                if (TextUtils.isEmpty(banknum)||TextUtils.isEmpty(bankname)){
                    ToastUtil.show(getActivity(),getResources().getString(R.string.accountisempty));
                }else{

                }
                //等待接口
                break;

        }
    }
}
