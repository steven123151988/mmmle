package com.xhqb.lotteryandsports.application;

public class XHApplicationUtil {

    /**
     * APP手动退出的入口
     */
    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
