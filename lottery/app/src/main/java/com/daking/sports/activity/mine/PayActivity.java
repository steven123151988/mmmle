package com.daking.sports.activity.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.fragment.pay.CompanyIncomeFragment;
import com.daking.sports.fragment.pay.PayOnlineFragment;

public class PayActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private RadioButton rb_left, rb_right;
    private CompanyIncomeFragment companyIncomeFragment;
    private PayOnlineFragment payOnlineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        getPayView();
    }

    private void initView() {
        rb_left = (RadioButton) findViewById(R.id.rb_left);
        rb_right = (RadioButton) findViewById(R.id.rb_right);
        rb_left.setOnClickListener(this);
        rb_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_left:
                getPayView();
                break;
            case R.id.rb_right:
                rb_left.setTextColor(getResources().getColor(R.color.gray_666666));
                rb_left.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                rb_right.setBackgroundColor(getResources().getColor(R.color.red_84201e));
                rb_right.setTextColor(getResources().getColor(R.color.white_ffffff));
                if (null==companyIncomeFragment){
                    companyIncomeFragment=new CompanyIncomeFragment();
                }
                mFragmentManager = getFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.view_fragment, companyIncomeFragment);
                mFragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }


    /**
     * 一进来展示的view
     */
    private void getPayView() {
        rb_left.setTextColor(getResources().getColor(R.color.white_ffffff));
        rb_left.setBackgroundColor(getResources().getColor(R.color.red_84201e));
        rb_right.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
        rb_right.setTextColor(getResources().getColor(R.color.gray_666666));
        if (null==payOnlineFragment){
            payOnlineFragment=new PayOnlineFragment();
        }
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.view_fragment, payOnlineFragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }
}
