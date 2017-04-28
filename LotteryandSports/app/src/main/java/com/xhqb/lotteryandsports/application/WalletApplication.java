package com.xhqb.lotteryandsports.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.xhqb.app.xhqblibs.log.XHLibNetworkLogUtil;
import com.xhqb.lotteryandsports.utils.LogUtil;


/**
 * 自定义的application类
 */
public class WalletApplication extends Application  {
    private static WalletApplication instance = null;
    public static WalletApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.setLevel(2);
        registerActivityLifecycleCallbacks();
    }



    /**
     * 获取当前activity的接口
     */
    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }



}
