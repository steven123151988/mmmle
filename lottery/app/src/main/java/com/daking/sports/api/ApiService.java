package com.daking.sports.api;


import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.AccountHistoryRsp;
import com.daking.sports.json.BallGQRsp;
import com.daking.sports.json.BettingDetailRsp;
import com.daking.sports.json.BettingRecordRsp;
import com.daking.sports.json.ConfigRsp;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.LotteryVersion;
import com.daking.sports.json.MemOnlineRsp;

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
     * 获取左侧menu信息
     */
    @POST(SportsAPI.HOME_MENU)
    Call<LoginRsp> getMainMenu(@Body RequestBody body);

    /**
     * 获取球类信息列表
     */
    @POST(SportsAPI.MATCH_LIST)
    Call<BallGQRsp> getBallMsg(@Body RequestBody body);


    /**
     * 获取球类信息列表
     */
    @POST(SportsAPI.BET_HIS)
    Call<AccountHistoryRsp> getBetHistory(@Body RequestBody body);

    /**
     * 赛事详情
     */
    @POST(SportsAPI.GET_MATCH)
    Call<BettingDetailRsp> getMatch(@Body RequestBody body);

    /**
     * 修改账户密码
     */
    @POST(SportsAPI.CHANGE_PWD)
    Call<LoginRsp> changePsw(@Body RequestBody body);


    /**
     * 赛事详情
     */

    @POST(SportsAPI.BET_BETTING)
    Call<BettingRecordRsp> betBetting(@Body RequestBody body);

    /**
     * 在线提款
     */
    @POST(SportsAPI.MEM_ONLINE)
    Call<MemOnlineRsp> memOnline(@Body RequestBody body);

    /**
     * 登出
     */
    @POST(SportsAPI.LOGIN_OUT)
    Call<LoginRsp> loginOut(@Body RequestBody body);

}