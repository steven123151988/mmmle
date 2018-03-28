package com.daking.sports.util;


import android.app.Activity;
import android.content.Intent;


import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.application.SportsApplication;

import org.greenrobot.eventbus.EventBus;

public class UserHelper {

    private static final String CURRENT_USER = "current_user";
    private static UserModel currUser;//当前用户

    private UserHelper() {
    }

    private static class SingletonHolder {
        static UserHelper INSTANCE = new UserHelper();
    }

    public static UserHelper get() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 设置当前用户
     *
     * @param user 当前用户
     */
    public void setCurrUser(UserModel user) {
        currUser = user;
        SharePreferencesUtil.addString(SportsApplication.getInstance(), CURRENT_USER, JsonUtil.toJson(user));
//        EventBus.getDefault().post(new UserChangedEvent(currUser));
        //webSocket
        if (user == null) {
            SocketHelper.get().disconnectWebSocket();
        } else {
            SocketHelper.get().connectWebSocket();
        }
    }

    /**
     * @return 当前用户
     */
    public UserModel getCurrUser() {
        if (currUser == null) {
            String jsonStr = SharePreferencesUtil.getString(SportsApplication.getInstance(), CURRENT_USER, null);
            currUser = JsonUtil.fromJson(jsonStr, UserModel.class);
        }
        return currUser;
    }

    /**
     * @return 用户id
     */
    public String getUserId() {
        UserModel user = getCurrUser();
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }

    /**
     * @return sessionId
     */
    public String getSessionId() {
        UserModel user = getCurrUser();
        if (user != null) {
            return user.getSessionId();
        } else {
            return null;
        }
    }

    /**
     * 退出登录
     */
    public void userSignOut() {
        setCurrUser(null);
        Activity activity = ActivityManager.getInstance().getCurrentActivity();
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

}
