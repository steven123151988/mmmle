package com.daking.sports.activity.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.base.BaseActivity;

/**
 * Created by 18 on 2017/5/28.  启动页
 */

public class SplashActivitt extends BaseActivity {
    private int sdk_version = Build.VERSION.SDK_INT;  // 进入之前获取手机的SDK版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //只适配SDK大于16的手机
        if (sdk_version > 15) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
