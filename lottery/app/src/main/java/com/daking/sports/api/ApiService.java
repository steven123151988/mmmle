package com.daking.sports.api;


import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.ConfigRsp;

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


}
