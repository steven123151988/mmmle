package com.daking.sports.activity.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsKey;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 18 on 2017/5/28.  启动页
 */

public class SplashActivity extends BaseActivity {
    private int sdk_version = Build.VERSION.SDK_INT;  // 进入之前获取手机的SDK版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //先判断网络情况,可以的话走下去，不可以的话提示网络有问题
        if (checkNetworkState()) {
            //只适配SDK大于16的手机
            if (sdk_version > 15) {
                //延迟5秒关闭
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initLogType();
                        finish();
                    }
                }, 2500);
            } else {
                ShowDialogUtil.showFailDialog(mContext,getString(R.string.loginerr), getString(R.string.app_support_lowest_sdk));
            }
        } else {
            ShowDialogUtil.showFailDialog(mContext,getString(R.string.loginerr),getString(R.string.net_error));
        }


    }



    /**
     * 查看登陆状态，若UID为空就要去登陆
     */
    private void initLogType() {
        if (SharePreferencesUtil.getString(mContext, SportsKey.UID, "0").equals("0")) {
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
    }
}