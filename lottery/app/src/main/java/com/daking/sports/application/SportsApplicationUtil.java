package com.daking.sports.application;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

public class SportsApplicationUtil {

    /**
     * APP手动退出的入口
     */
    public static void exit(Context context) {
        //退出前保存友盟统计数据
        MobclickAgent.onKillProcess(context);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
