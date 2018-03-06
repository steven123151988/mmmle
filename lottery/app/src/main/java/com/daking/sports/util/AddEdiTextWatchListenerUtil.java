package com.daking.sports.util;


import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.daking.sports.application.ActivityManager;

/**
 * Created by Sunny18 on 2017/6/16.监听editext的size数量
 */

public class AddEdiTextWatchListenerUtil {


    public static void addTextWatchListener(EditText editText, final int size) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String psw = s.toString();
                if (psw.length() == size) {
                    closeKeyboard();
                }
            }
        });
    }


    /**
     * 关闭键盘
     */
    private static void closeKeyboard() {
        if (null != ActivityManager.getInstance().getCurrentActivity()) {
            View view = ActivityManager.getInstance().getCurrentActivity().getWindow().peekDecorView();
            if (null != view) {
                InputMethodManager inputMethodManager = (InputMethodManager) ActivityManager.getInstance().getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


}
