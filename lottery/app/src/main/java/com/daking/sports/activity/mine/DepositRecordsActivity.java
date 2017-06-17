package com.daking.sports.activity.mine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsKey;
import com.daking.sports.fragment.IncomeAndTakeout.IncomeRecordsFragment;

/**
 * Created by 18 on 2017/5/9. 存取款记录
 */

public class DepositRecordsActivity  extends BaseActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;  // Fragment管理器
    private FragmentTransaction mFragmentTransaction;    // fragment事物
    private LinearLayout ll_incomerecords,ll_takeoutrecords;
    private IncomeRecordsFragment  incomeRecordsFragment;
    private TextView tv_center;
    private ImageView iv_back;
    private TextView tv_incomerecords,tv_takeoutrecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositrecords);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(getString(R.string.depositrecords));
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
        tv_incomerecords=fuck(R.id.tv_incomerecords);
        tv_takeoutrecords=fuck(R.id.tv_takeoutrecords);
        ll_incomerecords=fuck(R.id.ll_incomerecords);
        ll_incomerecords.setOnClickListener(this);
        ll_takeoutrecords=fuck(R.id.ll_takeoutrecords);
        ll_takeoutrecords.setOnClickListener(this);
        getFirstView();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_incomerecords:
                getFirstView();
                break;
            case R.id.ll_takeoutrecords:
                ll_incomerecords.setBackgroundColor(getResources().getColor(R.color.red_84201e));
                ll_takeoutrecords.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
                tv_incomerecords.setTextColor(getResources().getColor(R.color.white_ffffff));
                tv_takeoutrecords.setTextColor(getResources().getColor(R.color.black_08090b));
                setBallMessage("withdraw");
                showFragmentViews(incomeRecordsFragment);
                break;
        }
    }

    private void getFirstView() {
        ll_takeoutrecords.setBackgroundColor(getResources().getColor(R.color.red_84201e));
        ll_incomerecords.setBackgroundColor(getResources().getColor(R.color.white_ffffff));
        tv_takeoutrecords.setTextColor(getResources().getColor(R.color.white_ffffff));
        tv_incomerecords.setTextColor(getResources().getColor(R.color.black_08090b));
        setBallMessage("deposit");
        showFragmentViews(incomeRecordsFragment);
    }


    /**
     * 展示fragment界面
     *
     * @param fragment
     */
    public void showFragmentViews(Fragment fragment) {
        if (null != fragment) {
            mFragmentManager = getFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.view_fragment, fragment);
            mFragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * 设置球的数据
     *
     * @param type
     */
    public void setBallMessage(String type) {
        if (null == incomeRecordsFragment) {
            incomeRecordsFragment = new IncomeRecordsFragment();
        } else {
            incomeRecordsFragment = null;
            incomeRecordsFragment = new IncomeRecordsFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(SportsKey.TYPE, type);
        incomeRecordsFragment.setArguments(bundle);
    }
}
