package com.daking.sports.api;


import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.ConfigRsp;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.LotteryVersion;

import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * 请求全局变量
     */
    @POST(SportsAPI.CONFIG_INDEX)
    Call<ConfigRsp> getConfig(@Body RequestBody body);

    /**
     * 检查用户名
     */
    @POST(SportsAPI.CHECK_USER)
    Call<LoginRsp> checkUser(@Body RequestBody body);

    /**
     * 注册
     */
    @POST(SportsAPI.REGIST)
    Call<LoginRsp> gotoRegist(@Body RequestBody body);

    /**
     * 升级APP
     */
    @POST(SportsAPI.GET_VERSION_ANDROID)
    Call<LotteryVersion> appUpGrade(@Body RequestBody body);

    /**
     * 登陆
     */
    @POST(SportsAPI.LOGIN)
    Call<LoginRsp> login(@Body RequestBody body);

    /**
     *   获取左侧menu信息
     */
    @POST(SportsAPI.HOME_MENU)
    Call<LoginRsp> getMainmenu(@Body RequestBody body);

}
