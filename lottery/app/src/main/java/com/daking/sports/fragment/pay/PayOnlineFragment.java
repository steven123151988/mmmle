package com.daking.sports.fragment.pay;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/7.
 */

public class PayOnlineFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_first_view;
    private WebView mWebView;
    private LoginRsp loginRsp;
    private Gson gson;
    private Button btn_confirm_pay;
    private EditText et_money;
    private String money;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payonline, null);
        ll_first_view = (LinearLayout) view.findViewById(R.id.ll_first_view);
        mWebView = (WebView) view.findViewById(R.id.webview);
        et_money = (EditText) view.findViewById(R.id.et_money);
        btn_confirm_pay = (Button) view.findViewById(R.id.btn_confirm_pay);
        btn_confirm_pay.setOnClickListener(this);
        return view;
    }


    /**
     * 初始化webview
     */
    private void initWebview(String url) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // ------------加了这段就不会用浏览器打开网页----------
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // view.loadDataWithBaseURL(null, url, "text/html", "utf-8",
                // null);
                return false;
            }
        });
        // -----------------------
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    /**
     * 获取支付链接
     */
    private void getPayUrl() {
        RequestBody requestBody = new FormBody.Builder()
                .add("fnName", "yp")
                .add("uid", SharePreferencesUtil.getString(getActivity(), SportsKey.UID, ""))
                .build();

        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.GET_PAY_URL)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                LogUtil.e("===========msg==============" + msg);
                gson = new Gson();
                loginRsp = gson.fromJson(msg, LoginRsp.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (loginRsp.getCode() == 0) {
                                ll_first_view.setVisibility(View.GONE);
                                mWebView.setVisibility(View.VISIBLE);
                                initWebview(loginRsp.getIfo());
                            } else {
                                ll_first_view.setVisibility(View.VISIBLE);
                                mWebView.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_pay:
                money = et_money.getText().toString().replace(" ", "");
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.show(getActivity(), getString(R.string.please_typein_money));
                } else {
                    getPayUrl();
                }
                break;
        }
    }
}
