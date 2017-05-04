package com.daking.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daking.lottery.R;
import com.daking.lottery.activity.mine.AccessRecordActivity;
import com.daking.lottery.activity.mine.AccoutHistoryActivity;
import com.daking.lottery.activity.mine.LoginActivity;
import com.daking.lottery.activity.mine.ModifiPswActivity;
import com.daking.lottery.activity.mine.PersonalActivity;
import com.daking.lottery.activity.mine.QuietActivity;
import com.daking.lottery.activity.mine.RegistActivity;
import com.daking.lottery.activity.mine.TodayClearedActivity;
import com.daking.lottery.activity.mine.UnclearedActivity;
import com.daking.lottery.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;


public class MineFragment extends BaseFragment  implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        view.findViewById(R.id.rl_1).setOnClickListener(this);
        view.findViewById(R.id.rl_2).setOnClickListener(this);
        view.findViewById(R.id.rl_3).setOnClickListener(this);
        view.findViewById(R.id.rl_4).setOnClickListener(this);
        view.findViewById(R.id.rl_5).setOnClickListener(this);
        view.findViewById(R.id.rl_6).setOnClickListener(this);
        view.findViewById(R.id.rl_7).setOnClickListener(this);
        view.findViewById(R.id.rl_8).setOnClickListener(this);
        view.findViewById(R.id.bt_login).setOnClickListener(this);
        view.findViewById(R.id.bt_regist).setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineFragment");
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
                //未结明细
                startActivity(new Intent(getActivity(),UnclearedActivity.class));
                break;
            case R.id.rl_2:
                //今日已结
                startActivity(new Intent(getActivity(),TodayClearedActivity.class));
                break;
            case R.id.rl_3:
                //账户历史
                startActivity(new Intent(getActivity(),AccoutHistoryActivity.class));
                break;
            case R.id.rl_4:
                //存取记录
                startActivity(new Intent(getActivity(),AccessRecordActivity.class));
                break;
            case R.id.rl_5:
                //游戏规则

                break;
            case R.id.rl_6:
                //个人资料
                startActivity(new Intent(getActivity(),PersonalActivity.class));
                break;
            case R.id.rl_7:
                //修改密码
                startActivity(new Intent(getActivity(),ModifiPswActivity.class));
                break;
            case R.id.rl_8:
                //安全退出
                startActivity(new Intent(getActivity(),QuietActivity.class));
                break;
        }
    }
}
