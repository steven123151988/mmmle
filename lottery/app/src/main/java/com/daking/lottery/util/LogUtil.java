package com.daking.lottery.util;

import android.util.Log;

/**
 * Log管理类
 */
public class LogUtil {
    private static final String TAG = "lottery=>";
    public final static int LOG_LEVEL_DEBUG = 2;
    public final static int LOG_LEVEL_TEST = 1;
    public final static int LOG_LEVEL_RELEASE = 0;
    private static int level = LOG_LEVEL_DEBUG;
    /**
     * 默认level 为 LOG_LEVEL_RELEASE
     * 开发为 LOG_LEVEL_DEBUG，测试为 LOG_LEVEL_TEST，生产为：LOG_LEVEL_RELEASE
     * LOG_LEVEL_DEBUG的时候输出为log相同级别，比如log.e ,log.d
     * LOG_LEVEL_TEST的时候输出为均为log.i，区别显示
     * LOG_LEVEL_RELEASE,不打印日志
     */
    public static void setLevel(int level) {
        LogUtil.level = level;
        if (LogUtil.level > 0) {
            LogUtil.e("lotteryandsports日志级别为：" + (LogUtil.level == 1 ? "测试" : "开发"));
        }
    }

    public static int getLevel() {
        return level;
    }

    public static void e(String msg) {
        if (level <= LOG_LEVEL_RELEASE)
            return;
        else if (level == LOG_LEVEL_TEST) {
            Log.e(TAG, msg);
            return;
        } else if (level == LOG_LEVEL_DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {

        if (level <= LOG_LEVEL_RELEASE)
            return;
        else if (level == LOG_LEVEL_TEST) {
            Log.i(TAG, msg);
            return;
        } else if (level == LOG_LEVEL_DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void d(String msg) {

        if (level <= LOG_LEVEL_RELEASE)
            return;
        else if (level == LOG_LEVEL_TEST) {
            Log.i(TAG, msg);
            return;
        } else if (level == LOG_LEVEL_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void v(String msg) {

        if (level <= LOG_LEVEL_RELEASE)
            return;
        else if (level == LOG_LEVEL_TEST) {
            Log.i(TAG, msg);
            return;
        } else if (level == LOG_LEVEL_DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String msg) {

        if (level <= LOG_LEVEL_RELEASE)
            return;
        else if (level == LOG_LEVEL_TEST) {
            Log.i(TAG, msg);
            return;
        } else if (level == LOG_LEVEL_DEBUG) {
            Log.i(TAG, msg);
        }
    }
}
