package com.daking.sports.util;

import android.app.Activity;
import android.content.Context;

import com.daking.sports.R;
import com.daking.sports.base.SportsKey;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 18 on 2017/6/5 消息弹框的抽取，全局复用
 */

public class ShowDialogUtil {
    public static SweetAlertDialog sweetAlertDialog_success, sweetAlertDialog_fail;

    /**
     * 系统错误提示
     */
    public static void showSystemFail(Context mContext) {
        if (null == mContext) {
            return;
        }
        if (null != sweetAlertDialog_fail) {
            sweetAlertDialog_fail.cancel();
        }
        if (!((Activity) mContext).isFinishing()) {
            sweetAlertDialog_fail = new SweetAlertDialog(mContext, SportsKey.TYPE_ONE);
            sweetAlertDialog_fail.setTitleText(mContext.getString(R.string.sorry));
            sweetAlertDialog_fail.setContentText(mContext.getString(R.string.system_error));
            sweetAlertDialog_fail.show();
        }
    }

    /**
     * 提示成功信息
     *
     * @param mContext
     * @param title
     * @param message
     */
    public static void showSuccessDialog(Context mContext, String title, String message) {
        if (null == mContext) {
            return;
        }
        if (null != sweetAlertDialog_success) {
            sweetAlertDialog_success.cancel();
        }
        if (!((Activity) mContext).isFinishing()) {
            sweetAlertDialog_success = new SweetAlertDialog(mContext, SportsKey.TYPE_TWO);
            sweetAlertDialog_success.setTitleText(title);
            sweetAlertDialog_success.setContentText(message);
            sweetAlertDialog_success.show();
        }
    }

    /**
     * 提示失败信息
     *
     * @param mContext
     * @param title
     * @param message
     */
    public static void showFailDialog(Context mContext, String title, String message) {
        if (null == mContext) {
            return;
        }
        if (null != sweetAlertDialog_fail) {
            sweetAlertDialog_fail.cancel();
        }
        if (!((Activity) mContext).isFinishing()) {
            sweetAlertDialog_fail = new SweetAlertDialog(mContext, SportsKey.TYPE_ONE);
            sweetAlertDialog_fail.setTitleText(title);
            sweetAlertDialog_fail.setContentText(message);
            sweetAlertDialog_fail.show();
        }


    }

    /**
     * 取消对话框
     */
    public static void dismissDialogs() {
        if (null != sweetAlertDialog_success) {
            sweetAlertDialog_success.cancel();
        }
        if (null != sweetAlertDialog_fail) {
            sweetAlertDialog_fail.cancel();
        }
    }
}
