package com.daking.lottery.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.daking.lottery.util.LogUtil;
import com.umeng.analytics.MobclickAgent;


/**
 * 自定义的application类
 */
public class LotteryApplication extends Application  {
    private static LotteryApplication instance = null;
    public static LotteryApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //设置日志等级
        LogUtil.setLevel(2);
        //设置友盟统计场景
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType. E_UM_NORMAL);
        //友盟日志加密6.0.0版本及以后
        MobclickAgent.enableEncrypt(true);

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
                ActivityManager.getInstance().setCurrentActivity(activity);
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
