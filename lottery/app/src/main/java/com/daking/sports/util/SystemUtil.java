package com.daking.sports.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Steven on 2017/3/29.  系统相关的工具类
 */

public class SystemUtil {

    // 判断程序是否在前台运行
    private boolean isAppOnForeground(Context  context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if (processInfos == null) {
            return false;
        }
        for(ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.processName.equals(packageName)
                    && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
