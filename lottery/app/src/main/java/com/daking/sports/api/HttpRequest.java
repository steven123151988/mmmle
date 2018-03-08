package com.daking.sports.api;

import android.support.v4.util.ArrayMap;

import com.daking.sports.base.SportsKey;
import com.daking.sports.json.AccountHistoryRsp;
import com.daking.sports.json.BallGQRsp;
import com.daking.sports.json.BettingDetailRsp;
import com.daking.sports.json.BettingRecordRsp;
import com.daking.sports.json.ConfigRsp;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.LotteryVersion;
import com.daking.sports.json.MemOnlineRsp;
import com.daking.sports.util.JsonUtil;
import com.daking.sports.util.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


/**
 * 描述：网络请求类，将网络请求的方法放到此处统一管理  单例
 * 每个请求方法都传了tag标签,Activity和Fragment中请传this,方便生命周期管理.
 */

public class HttpRequest {

    private ApiService mService = ApiClient.getInstance().mApiService;
    private static ArrayMap<Object, List<Call>> mCallMap = new ArrayMap<>();

    private HttpRequest() {
    }

    private static class SingletonHolder {
        private static HttpRequest instance = new HttpRequest();
    }

    public static HttpRequest getInstance() {
        return SingletonHolder.instance;
    }

    private synchronized void putCall(Object tag, Call call) {
        if (tag == null) {
            return;
        }
        List<Call> callList = mCallMap.get(tag);
        if (callList == null) {
            callList = Collections.synchronizedList(new ArrayList<Call>());
        }
        callList.add(call);
        mCallMap.put(tag, callList);
    }

    public synchronized void cancelRequest(Object tag) {
        if (tag == null) {
            return;
        }
        List<Call> callList = mCallMap.get(tag);
        if (callList == null || callList.size() == 0) {
            return;
        }
        for (Call call : callList) {
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
        mCallMap.remove(tag);
    }

    private class RequestBodyBuilder {

        Map<String, Object> params;

        private RequestBodyBuilder() {
            params = new HashMap<>();
        }

        private RequestBodyBuilder addParam(String key, Object value) {
            params.put(key, value);
            return this;
        }

        private RequestBody build() {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(params));
        }
    }


    public void getConfig(Object tag, HttpCallback<ConfigRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, SportsKey.CONFIG)
                .addParam(SportsKey.HOST, "le5")
                .build();
        Call<ConfigRsp> call = mService.getConfig(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 检查用户名是否已经被占用
     *
     * @param tag
     * @param callback
     */
    public void checkUser(Object tag, String account, HttpCallback<LoginRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, "chk_user")
                .addParam(SportsKey.USER_NAME, account)
                .build();
        Call<LoginRsp> call = mService.checkUser(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 注册账户
     *
     * @param tag
     * @param account
     * @param psw
     * @param name
     * @param check_question
     * @param answer
     * @param money_psw
     * @param birthday
     * @param callback
     */
    public void gotoRegist(Object tag, String account, String psw,
                           String name, String check_question, String answer,
                           String money_psw, String birthday, HttpCallback<LoginRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("fnName", "reg")
                .addParam("keys", "add")
                .addParam("website", "android")
                .addParam("website1", "android")
                .addParam("reg", "3")
                .addParam("username", account)//账号
                .addParam("password", psw)//密码
                .addParam("currency", "RMB")  //首选货币
                .addParam("alias", name)  //真实姓名
                .addParam("question", check_question) //密码提示问题
                .addParam("answer", answer)//答案
                .addParam("drpAuthCode", money_psw)
                .addParam("birthday", birthday)
                .addParam("contory", "中国")
                .addParam("city", "中国")
                .addParam("know_site", "0")
                .addParam("Checkbox", "1")  //是否选中已经满18岁
                .build();
        Call<LoginRsp> call = mService.gotoRegist(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * app版本升级
     *
     * @param tag
     * @param callback
     */
    public void appUpGrade(Object tag, HttpCallback<LotteryVersion> callback) {
        RequestBody body = new RequestBodyBuilder()
                .build();
        Call<LotteryVersion> call = mService.appUpGrade(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 登陆
     *
     * @param tag
     * @param callback
     */
    public void login(Object tag, String account, String psw, HttpCallback<LoginRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.USER_NAME, account)
                .addParam(SportsKey.PASSWORD, psw)
                .addParam(SportsKey.FNNAME, SportsKey.LOGIN)
                .addParam(SportsKey.LANGUAGE, SportsKey.ZH_CN)
                .build();
        Call<LoginRsp> call = mService.login(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取左侧菜单的menu数据
     *
     * @param tag
     * @param uid
     * @param callback
     */
    public void getHomeMenu(Object tag, String uid, HttpCallback<LoginRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, "menu")
                .addParam(SportsKey.UID, uid)
                .build();
        Call<LoginRsp> call = mService.getMainMenu(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取球类信息列表
     *
     * @param tag
     * @param uid
     * @param ball
     * @param type
     * @param callback
     */
    public void getBallMsg(Object tag, String uid, String ball, String type, HttpCallback<BallGQRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, "mlist")
                .addParam(SportsKey.UID, uid)
                .addParam(SportsKey.BALL, ball)
                .addParam(SportsKey.TYPE, type)
                .build();
        Call<BallGQRsp> call = mService.getBallMsg(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取账户历史
     *
     * @param tag
     * @param uid
     * @param callback
     */
    public void getBetHistory(Object tag, String uid, HttpCallback<AccountHistoryRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, "bet_his")
                .addParam(SportsKey.UID, uid)
                .build();
        Call<AccountHistoryRsp> call = mService.getBetHistory(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取赛事详情
     *
     * @param tag
     * @param uid
     * @param mid
     * @param callback
     */
    public void getMatch(Object tag, String uid, String mid, HttpCallback<BettingDetailRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, "selmatch")
                .addParam(SportsKey.UID, uid)
                .addParam(SportsKey.MID, mid)
                .build();
        Call<BettingDetailRsp> call = mService.getMatch(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 修改密码
     *
     * @param tag
     * @param psw1
     * @param newPSW
     * @param type
     * @param callback
     */
    public void changePsw(Object tag, String uid, String psw1, String newPSW, String type, HttpCallback<LoginRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.UID, uid)
                .addParam(SportsKey.FNNAME, "chg_pwd")
                .addParam(SportsKey.OLD_PWD, psw1)
                .addParam(SportsKey.BEW_PWD, newPSW)
                .addParam(SportsKey.TYPE, type)
                .build();
        Call<LoginRsp> call = mService.changePsw(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *   获取赛事详情
     * @param tag
     * @param uid
     * @param ball
     * @param page
     * @param callback
     */
    public void betBetting(Object tag, String uid,  String ball, int page, HttpCallback<BettingRecordRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.UID, uid)
                .addParam(SportsKey.FNNAME, "betlist")
                .addParam(SportsKey.BALL, ball)
                .addParam(SportsKey.PAGE, page)
                .build();
        Call<BettingRecordRsp> call = mService.betBetting(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *  在线提款
     * @param tag
     * @param uid
     * @param callback
     */
    public void memOnline(Object tag, String uid,  HttpCallback<MemOnlineRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.UID, uid)
                .addParam(SportsKey.FNNAME, "withdrawals")
                .build();
        Call<MemOnlineRsp> call = mService.memOnline(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *  登出
     * @param tag
     * @param uid
     * @param callback
     */
    public void loginOut(Object tag, String uid,  HttpCallback<LoginRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.UID, uid)
                .addParam(SportsKey.FNNAME,  SportsKey.LOGIN_OUT)
                .build();
        Call<LoginRsp> call = mService.loginOut(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

}
