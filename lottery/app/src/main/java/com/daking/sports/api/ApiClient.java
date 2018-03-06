package com.daking.sports.api;

import com.daking.sports.BuildConfig;
import com.daking.sports.base.SportsAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private volatile static ApiClient instance;
    public final ApiService mApiService;

    public static ApiClient getInstance() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {
                    instance = new ApiClient();
                }
            }
        }
        return instance;
    }

    private ApiClient() {
        //日志拦截
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient httpClient = new OkHttpClient.Builder()
                //读取超时时间
                .readTimeout(1000 * 10, TimeUnit.MILLISECONDS)
                //设置连接超时时间
                .connectTimeout(1000 * 10, TimeUnit.MILLISECONDS)
                //请求日志
                .addInterceptor(logInterceptor)
                .build();

        /*
        * Gson默认处理Date对象的序列化/反序列化是通过一个SimpleDateFormat对象来实现的，
        * 会通过代码：DateFormat.getDateTimeInstance() 去获取实例
        * 在不同的locale环境中，这样获取到的SimpleDateFormat的模式字符串会不一样。
        * 为了避免使用Gson时遇到locale影响Date格式的问题，使用GsonBuilder来创建Gson对象，
        * 在创建过程中调用GsonBuilder.setDateFormat(String)指定一个固定的格式即可。
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                //设置以上配置的httpClient
                .client(httpClient)
                //添加gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                //设置网络请求的主机地址
                .baseUrl(SportsAPI.BASE_URL)
                .build();

        mApiService = retrofit.create(ApiService.class);
    }
}
