package com.daking.sports.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.activity.mine.LoginActivity;
import com.daking.sports.activity.mine.PayActivity;
import com.daking.sports.activity.mine.PswManagerActivity;
import com.daking.sports.activity.mine.RegistActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsId;
import com.umeng.analytics.MobclickAgent;


public class PersonalCenterFragment extends BaseFragment  implements View.OnClickListener{
    private ServiceFragment serviceFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, null);
        view.findViewById(R.id.rl_1).setOnClickListener(this);
        view.findViewById(R.id.rl_2).setOnClickListener(this);
        view.findViewById(R.id.rl_3).setOnClickListener(this);
        view.findViewById(R.id.rl_4).setOnClickListener(this);
        view.findViewById(R.id.rl_5).setOnClickListener(this);
        view.findViewById(R.id.rl_6).setOnClickListener(this);
        view.findViewById(R.id.bt_login).setOnClickListener(this);
        view.findViewById(R.id.bt_regist).setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PersonalCenterFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PersonalCenterFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case  R.id.bt_login:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case  R.id.bt_regist:
                startActivity(new Intent(getActivity(),RegistActivity.class));
                break;
            case R.id.rl_1:
                //充值
                startActivity(new Intent(getActivity(),PayActivity.class));
                break;
            case R.id.rl_2:
                //提款
                break;
            case R.id.rl_3:
                //投注记录
                break;
            case R.id.rl_4:
                //账户明细
                break;
            case R.id.rl_5:
                //密码管理
                startActivity(new Intent(getActivity(),PswManagerActivity.class));
                break;
            case R.id.rl_6:
                //客服
                if (null==serviceFragment){
                    serviceFragment=new ServiceFragment();
                }
                ((MainActivity)getActivity()).showFragmentViews(SportsId.TYPE_SIX,serviceFragment);
                break;
        }
    }
}
