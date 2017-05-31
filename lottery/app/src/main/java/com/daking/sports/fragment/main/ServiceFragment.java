package com.daking.sports.fragment.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.umeng.analytics.MobclickAgent;


public class ServiceFragment extends BaseFragment implements View.OnClickListener {
    private WebView mWebView;
    private ImageView mIvBack;
    private FirstFragment firstFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, null);
        ImageView iv_center=(ImageView) view.findViewById(R.id.iv_center);
        iv_center.setVisibility(View.VISIBLE);
        mWebView = (WebView) view.findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); }

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


        mWebView.loadUrl(SportsAPI.SERVICE_URL);
        mIvBack=(ImageView) view.findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ServiceFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ServiceFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                if (null==firstFragment){
                    firstFragment=new FirstFragment();
                }
                ((MainActivity)getActivity()).showFragmentViews(SportsKey.TYPE_ONE,firstFragment);
                break;
        }


    }
}
