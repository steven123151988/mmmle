package com.daking.sports.api;

import android.support.v4.util.ArrayMap;

import com.daking.sports.base.SportsKey;
import com.daking.sports.json.ConfigRsp;
import com.daking.sports.util.JsonUtil;

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

    public synchronized void cancel(Object tag) {
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

//    /**
//     * 用户注册
//     *
//     * @param username    用户名
//     * @param password    密码
//     * @param realName    真实姓名
//     * @param payPassword 支付密码
//     * @param qqskype     邮箱(可不传)
//     * @param telephone   电话(可不传)
//     * @param parentName  代理名称(可不传)
//     */
//    public void register(Object tag, String username, String password, String realName,
//                         String payPassword, @Nullable String qqskype, @Nullable String telephone,
//                         @Nullable String parentName, HttpCallback<BaseModel> callback) {
//        RequestBody body = new RequestBodyBuilder()
//                .addParam("username", username)
//                .addParam("password", password)
//                .addParam("realname", realName)
//                .addParam("paypasswd", payPassword)
//                .addParam("qqskype", qqskype)
//                .addParam("telphone", telephone)
//                .addParam("parentname", parentName)
//                .build();
//        Call<BaseModel> call = mService.getConfig(body);
//        putCall(tag, call);
//        call.enqueue(callback);
//    }


    public void getConfig(Object tag, HttpCallback<ConfigRsp> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(SportsKey.FNNAME, SportsKey.CONFIG)
                .addParam(SportsKey.HOST, "le5")
                .build();
        Call<ConfigRsp> call = mService.getConfig(body);
        putCall(tag, call);
        call.enqueue(callback);

    }
}
