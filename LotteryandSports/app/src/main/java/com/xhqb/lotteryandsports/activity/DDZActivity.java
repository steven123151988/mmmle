package com.xhqb.lotteryandsports.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xhqb.lotteryandsports.R;
import com.xhqb.lotteryandsports.base.BaseActivity;
import com.xhqb.lotteryandsports.fragment.GameFragment;
import com.xhqb.lotteryandsports.fragment.MenuFragment;

/**
 * Created by quchuangye on 2017/1114/6.
 */

public class DDZActivity extends BaseActivity implements OnClickListener {
    // Fragment管理器
    private FragmentManager mFragmentManager;
    // fragment事物
    private FragmentTransaction mFragmentTransaction;
    private MenuFragment menuFragment;
    private GameFragment gameFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddz);

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(null==menuFragment){
            menuFragment = new MenuFragment();
        }
        mFragmentTransaction.replace(R.id.view_fragment, menuFragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onClick(View v) {

    }
}
