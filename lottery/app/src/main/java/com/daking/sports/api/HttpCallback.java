package com.daking.sports.api;

import android.support.annotation.NonNull;

import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.BaseModel;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<T extends BaseModel> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (call.isCanceled()) {
            return;
        }
        if (response.isSuccessful()) {
            T model = response.body();
            if (model == null) {
                return;
            }
            /**
             *  接口code=0表示成功, code=1表示失败
             */
            if (model.getCode() == 0) {
                onSuccess(model);
            } else {
                onApiFailure(model);
            }
        } else {
            onFailure(call, new IOException("Unexpected code " + response.code()));
        }
    }

    private void onApiFailure(T model) {
        String msgCode = model.getMsg();
        onFailure(msgCode, SportsAPI.getErrorCodeInfo(msgCode));
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        t.printStackTrace();
        if (call.isCanceled()) {
            return;
        }
        if (t instanceof JSONException || t instanceof JsonParseException || t instanceof ParseException) {
            onFailure("10011", SportsAPI.getErrorCodeInfo("10011"));
        } else if (t instanceof ConnectException) {
            onFailure("10012", SportsAPI.getErrorCodeInfo("10012"));
        } else if (t instanceof SSLHandshakeException) {
            onFailure("10013", SportsAPI.getErrorCodeInfo("10013"));
        } else if (t instanceof UnknownHostException) {
            onFailure("10014", SportsAPI.getErrorCodeInfo("10014"));
        } else if (t instanceof SocketTimeoutException) {
            onFailure("10015", SportsAPI.getErrorCodeInfo("10015"));
        } else {
            onFailure("10016", SportsAPI.getErrorCodeInfo("10016"));
        }
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(String msgCode, String errorMsg);
}
