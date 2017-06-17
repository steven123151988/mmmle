package com.daking.sports.activity.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.daking.sports.R;
import com.daking.sports.base.BaseActivity;
import com.daking.sports.base.SportsKey;


public class WebViewActivity extends BaseActivity implements OnClickListener {
	private ImageView mIvBack;
	private TextView mTvCenter;
	private WebView mWebView;
	private String title;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		Bundle data = getIntent().getExtras();
		title = data.getString(SportsKey.WEBVIEW_TITLE);
		url = data.getString(SportsKey.WEBVIEW_URL);
		initView();
	}

	private void initView() {
		mIvBack = (ImageView) findViewById(R.id.iv_back);
		mTvCenter = (TextView) findViewById(R.id.tv_center);
		mWebView = (WebView) findViewById(R.id.webview);

		mIvBack.setVisibility(ImageView.VISIBLE);
		mTvCenter.setVisibility(TextView.VISIBLE);
		mTvCenter.setText(title);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setUserAgentString("app");
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
		mWebView.loadUrl(url);
		mIvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
				finish();
			break;

		default:
			break;
		}
	}
}
