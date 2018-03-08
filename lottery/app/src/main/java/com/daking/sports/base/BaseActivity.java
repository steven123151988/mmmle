package com.daking.sports.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daking.sports.api.HttpRequest;
import com.daking.sports.view.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class BaseActivity extends AppCompatActivity {
    public Context mContext;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        PushAgent.getInstance(mContext).onAppStart();
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

    protected <T extends View> T fuck(int id) {
        return (T) super.findViewById(id);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequest.getInstance().cancelRequest(this);
    }
}
