package com.daking.sports.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.daking.sports.application.ActivityManager;

/**
 * Created by Administrator on 2017/6/16.
 */

public class NetUtil {
    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean checkNetworkState() {
        boolean flag = false;
        if (null != ActivityManager.getInstance().getCurrentActivity()) {
            //得到网络连接信息
            ConnectivityManager manager = (ConnectivityManager) ActivityManager.getInstance().getCurrentActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            //去进行判断网络是否连接
            if (manager.getActiveNetworkInfo() != null) {
                flag = manager.getActiveNetworkInfo().isAvailable();
            }
        }
        return flag;
    }
}
