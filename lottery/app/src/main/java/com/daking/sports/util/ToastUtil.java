package com.daking.sports.util;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
    private static Toast mToast = null;


	public static void show(Context context, CharSequence text) {
        if (null != mToast) {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
	}


	public static void show(Context context, int resId) {
        if (null != mToast) {
            mToast.setText(resId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }
        mToast.show();
	}
}
