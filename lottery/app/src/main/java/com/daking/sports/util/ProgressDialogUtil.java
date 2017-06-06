package com.daking.sports.util;

import android.content.Context;
import android.content.DialogInterface;

import com.daking.sports.view.CustomProgressDialog;


/**
 * Created by Steven on 2017/4/1.
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

    public static void hideWaiting() {
        if (null != dialog) {
            dialog.dismiss();
            dialog = null;
        }

    }
}
