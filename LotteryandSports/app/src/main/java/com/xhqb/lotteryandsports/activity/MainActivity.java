package com.xhqb.lotteryandsports.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xhqb.lotteryandsports.R;
import com.xhqb.lotteryandsports.base.BaseActivity;
import com.xhqb.lotteryandsports.utils.SelsetRandomNumber;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by quchuangye on 2017/3/23.APP的主类
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks , View.OnClickListener{
    private PullToRefreshView mPullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] perms = {Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            getwifi();
        } else {
            EasyPermissions.requestPermissions(this, "READ_PHONE_STATE", 1, perms);
        }

//        SweetAlertDialog sd = new SweetAlertDialog(this);
//        sd.setCancelable(true);
//        sd.setCanceledOnTouchOutside(true);
//        sd.show();



//        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
//        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPullToRefreshView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mPullToRefreshView.setRefreshing(false);
//                    }
//                }, 500);
//            }
//        });

        SelsetRandomNumber.getRandomIntWithoutReduplicate(1,36,7);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    private void getwifi() {

    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("成功","123333");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("失败","123333");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case  R.id.button:
                Intent intent=new Intent(mContext,LotteryActivity.class);
                startActivity(intent);
                break;
            case  R.id.button2:
                Intent intent2=new Intent(mContext,DDZActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
