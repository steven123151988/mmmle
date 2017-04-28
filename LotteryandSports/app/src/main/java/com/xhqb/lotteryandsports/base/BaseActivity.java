package com.xhqb.lotteryandsports.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.xhqb.lotteryandsports.R;
import com.xhqb.lotteryandsports.utils.LogUtil;
import com.xhqb.lotteryandsports.view.CustomProgressDialog;

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
