package com.daking.lottery.util;

import android.content.Context;
import android.content.DialogInterface;

import com.daking.lottery.view.CustomProgressDialog;


/**
 * Created by quchuangye on 2017/4/1.
 */

public class ProgressDialogUtil {

    public Context mContext;
    private static CustomProgressDialog dialog;

    public static void showWaiting(Context mContext) {
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
