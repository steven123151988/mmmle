package com.daking.lottery.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import com.daking.lottery.view.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;


public class BaseActivity extends Activity {
    public Context mContext;
    private String className;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        String cn = component.getClassName();
        className = cn.substring(cn.lastIndexOf(".") + 1, cn.length());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟数据统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void showWaiting() {
        if (dialog == null) {
            dialog = new CustomProgressDialog(mContext);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    public void hideWaiting() {
        if (null != dialog) {
            dialog.dismiss();
            dialog = null;
        }

    }


}
