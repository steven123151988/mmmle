package com.daking.sports.fragment.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsKey;
import com.daking.sports.fragment.betting.BallFragment;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;

/**
 * 投注面页
 */
public class BettingFragment extends BaseFragment implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private LinearLayout ll_football, ll_basketball, ll_shixun;
    private TextView tv_football, tv_basketball, tv_shixun;
    private BallFragment ballFragment;
    private View view_betting;
    private String ball, type;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_betting, null);
        ll_football = (LinearLayout) view.findViewById(R.id.ll_football);
        ll_football.setOnClickListener(this);
        ll_basketball = (LinearLayout) view.findViewById(R.id.ll_basketball);
        ll_basketball.setOnClickListener(this);
        ll_shixun = (LinearLayout) view.findViewById(R.id.ll_shixun);
        ll_shixun.setOnClickListener(this);
        tv_football = (TextView) view.findViewById(R.id.tv_football);
        tv_basketball = (TextView) view.findViewById(R.id.tv_basketball);
        tv_shixun = (TextView) view.findViewById(R.id.tv_shixun);
        view_betting = view.findViewById(R.id.view_fragment2);
        if (null != getArguments().getString(SportsKey.BALL)) {
            ball = getArguments().getString(SportsKey.BALL);
        }
        if (null != getArguments().getString(SportsKey.TYPE)) {
            type = getArguments().getString(SportsKey.TYPE);
        }
        if (null != ball && null != type) {
            getballView(ball, type);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BettingFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BettingFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_football:
                ((MainActivity)getActivity()).setToolbar(getString(R.string.football_betting));
                getballView(SportsKey.FOOTBALL, "");
                break;
            case R.id.ll_basketball:
                ((MainActivity)getActivity()).setToolbar(getString(R.string.basketball_betting));
                getballView(SportsKey.BASKETBALL, "");
                break;
//            case R.id.ll_shixun:
//                ((MainActivity)getActivity()).setToolbar(getString(R.string.volleyball_betting));
//                ll_football.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
//                ll_basketball.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
//                ll_shixun.setBackgroundColor(getResources().getColor(R.color.red_84201e));
//                tv_football.setTextColor(getResources().getColor(R.color.black_08090b));
//                tv_basketball.setTextColor(getResources().getColor(R.color.black_08090b));
//                tv_shixun.setTextColor(getResources().getColor(R.color.white_ffffff));
//                if (null == shixunFragment) {
//                    shixunFragment = new ShixunFragment();
//                }
//                showView(shixunFragment);
//                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getballView(String ball, String type) {
        switch(ball){
            case SportsKey.FOOTBALL:
                ll_football.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                ll_basketball.setBackgroundColor(getResources().getColor(R.color.red_84201e));
                ll_shixun.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                tv_football.setTextColor(getResources().getColor(R.color.black_08090b));
                tv_basketball.setTextColor(getResources().getColor(R.color.white_ffffff));
                tv_shixun.setTextColor(getResources().getColor(R.color.black_08090b));
                break;
            case SportsKey.BASKETBALL:
                ll_football.setBackgroundColor(getResources().getColor(R.color.red_84201e));
                ll_basketball.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                ll_shixun.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                tv_football.setTextColor(getResources().getColor(R.color.white_ffffff));
                tv_basketball.setTextColor(getResources().getColor(R.color.black_08090b));
                tv_shixun.setTextColor(getResources().getColor(R.color.black_08090b));
                break;
        }
        setBallMessage(ball, type);
        showView(ballFragment);
    }

    /**
     * 展示fragment布局
     *
     * @param fragment 因为getChildFragmentManager();在17才有所以引用
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showView(Fragment fragment) {
        mFragmentManager = getChildFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.view_fragment2, fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * To solve fragment  include fragment case Activity has been destroyed.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置球的数据
     *
     * @param type
     */
    public void setBallMessage(String ball, String type) {
        if (null == ballFragment) {
            ballFragment = new BallFragment();
        } else {
            ballFragment = null;
            ballFragment = new BallFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(SportsKey.TYPE, type);
        bundle.putString(SportsKey.BALL, ball);
        ballFragment.setArguments(bundle);
    }

}
