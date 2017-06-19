package com.daking.sports.application;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.daking.sports.util.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;


/**
 * 自定义的application类
 */
public class SportsApplication extends MultiDexApplication {
    public static SportsApplication instance = null;
    public static SportsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //设置日志等级
        LogUtil.setLevel(2);
        //设置友盟统计场景
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        //友盟日志加密6.0.0版本及以后
        MobclickAgent.enableEncrypt(true);
        //注册友盟推送
        initUmengPush();
        registerActivityLifecycleCallbacks();
    }

    /**
     * 注册友盟推送信息
     */
    private void initUmengPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e("=======deviceToken=======" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });
        //关闭推送日志输出
        mPushAgent.setDebugMode(false);
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
