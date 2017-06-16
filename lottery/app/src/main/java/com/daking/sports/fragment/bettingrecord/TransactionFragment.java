package com.daking.sports.fragment.bettingrecord;

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
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsKey;

import java.lang.reflect.Field;

/**
 *   投注记录
 */
public class TransactionFragment extends BaseFragment implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private LinearLayout ll_football, ll_basketball;
    private TextView tv_football, tv_basketball;
    private View view_betting;
    private BettingRecordFragment bettingRecordFragment;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bet_recourd, null);
        ll_football = (LinearLayout) view.findViewById(R.id.ll_football);
        ll_football.setOnClickListener(this);
        ll_basketball = (LinearLayout) view.findViewById(R.id.ll_basketball);
        ll_basketball.setOnClickListener(this);
        tv_football = (TextView) view.findViewById(R.id.tv_football);
        tv_basketball = (TextView) view.findViewById(R.id.tv_basketball);
        view_betting = view.findViewById(R.id.view_fragment2);
        getballView(SportsKey.FOOTBALL);
        return view;
    }


    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_football:
                getballView(SportsKey.FOOTBALL);
                break;
            case R.id.ll_basketball:
                getballView(SportsKey.BASKETBALL);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getballView(String ball) {
        switch (ball) {
            case SportsKey.FOOTBALL:
                ll_football.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                ll_basketball.setBackgroundColor(getResources().getColor(R.color.red_84201e));
                tv_football.setTextColor(getResources().getColor(R.color.black_08090b));
                tv_basketball.setTextColor(getResources().getColor(R.color.white_ffffff));
                break;
            case SportsKey.BASKETBALL:
                ll_football.setBackgroundColor(getResources().getColor(R.color.red_84201e));
                ll_basketball.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                tv_football.setTextColor(getResources().getColor(R.color.white_ffffff));
                tv_basketball.setTextColor(getResources().getColor(R.color.black_08090b));
                break;
        }
        setBallMessage(ball);
        showView(bettingRecordFragment);
    }

    /**
     * 设置球的数据
     */
    public void setBallMessage(String ball) {
        if (null == bettingRecordFragment) {
            bettingRecordFragment = new BettingRecordFragment();
        } else {
            bettingRecordFragment = null;
            bettingRecordFragment = new BettingRecordFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(SportsKey.BALL, ball);
        bettingRecordFragment.setArguments(bundle);
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

}
