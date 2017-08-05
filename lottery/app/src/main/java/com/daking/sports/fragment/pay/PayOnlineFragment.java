package com.daking.sports.fragment.pay;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.base.SportsKey;
import com.daking.sports.json.PayIncomeRsp;
import com.daking.sports.json.PayStypeRsp;
import com.daking.sports.util.CloseSoftInputFromWindowUtil;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.util.ToastUtil;
import com.google.gson.Gson;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 18 on 2017/5/7. 在线入款面页
 */

public class PayOnlineFragment extends BaseFragment implements View.OnClickListener, View.OnLayoutChangeListener {
    private LinearLayout ll_first_view;
    private WebView mWebView;
    private Button btn_confirm_pay;
    private EditText et_money;
    private int money;
    private PayStypeRsp payStypeRsp;
    private List list_name;
    private SweetSheet mSweetSheet;
    private RelativeLayout rl;
    private MenuEntity menuEntity;
    private TextView tv_type;
    private String type;
    private int choose_position = 1;
    private String message;
    private int min, max;//能充值的最大值最小值
    private PayIncomeRsp payIncomeRsp;
    private Gson gson = new Gson();
    private long mClickTime;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private boolean ifopen = false;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payonline, null);
        ll_first_view = (LinearLayout) view.findViewById(R.id.ll_first_view);
        mWebView = (WebView) view.findViewById(R.id.webview);
        et_money = (EditText) view.findViewById(R.id.et_money);
        btn_confirm_pay = (Button) view.findViewById(R.id.btn_confirm_pay);
        rl = (RelativeLayout) view.findViewById(R.id.rl);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        btn_confirm_pay.setOnClickListener(this);
        view.findViewById(R.id.rl_choose_type).setOnClickListener(this);
        //获取屏幕高度
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/4
        keyHeight = screenHeight / 5;
        LogUtil.e("===================pay====");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        rl.addOnLayoutChangeListener(this);
        //清除上次填写的金额
        et_money.getText().clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_choose_type://选择充值方式
                //避免多次请求
                long time = System.currentTimeMillis();
                if (time - mClickTime <= 2500) {
                    ToastUtil.show(getActivity(), getString(R.string.not_click_manytimes));
                    return;
                } else {
                    mClickTime = time;
                    if (ifopen) {
                        CloseSoftInputFromWindowUtil.closeSoftInputFromWindow();
                        if (null == handler) {
                            handler = new Handler();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getPayUrl();
                            }
                        }, 150);
                    } else {
                        getPayUrl();
                    }

                }
                break;

            case R.id.btn_confirm_pay:
                if (TextUtils.isEmpty(et_money.getText().toString().replace(" ", ""))) {
                    ToastUtil.show(getActivity(), getString(R.string.please_typein_money));
                    return;
                }
                if (TextUtils.isEmpty(type)) {
                    ToastUtil.show(getActivity(), getString(R.string.please_choose_pay_type));
                    return;
                }
                money = Integer.parseInt(et_money.getText().toString().replace(" ", ""));
                if (null != payStypeRsp) {
                    max = Integer.parseInt(payStypeRsp.getIfo().get(choose_position).getMax());
                    min = Integer.parseInt(payStypeRsp.getIfo().get(choose_position).getMin());
                }
                if (money < min) {
                    ToastUtil.show(getActivity(), "该支付方式最小充值金额为" + min);
                    return;
                }
                if (money > max) {
                    ToastUtil.show(getActivity(), "该支付方式最大充值金额为" + max);
                    return;
                }
                if (money >= min && money <= max) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (payStypeRsp.getCode() == 0) {
                                    get3pay();
                                } else {
                                    ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), payStypeRsp.getMsg());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
        }
    }


    /**
     * 获取支付链接
     */
    private void getPayUrl() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.INCOME)
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.GET_PAY_URL)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LogUtil.e("=====getPayUrl=========" + message);
                                gson = new Gson();
                                payStypeRsp = gson.fromJson(message, PayStypeRsp.class);
                                if (null == payStypeRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (payStypeRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        list_name = new ArrayList<>();
                                        int size = payStypeRsp.getIfo().size();
                                        for (int i = 0; i < size; i++) {
                                            list_name.add(payStypeRsp.getIfo().get(i).getTitle());
                                        }
                                        if (null == list_name) {
                                            ToastUtil.show(getActivity(), "暂无有效的充值方式");
                                            return;
                                        }
                                        final ArrayList<MenuEntity> list = new ArrayList<>();
                                        for (int i = 0; i < list_name.size(); i++) {
                                            menuEntity = new MenuEntity();
                                            menuEntity.iconId = R.mipmap.company_income;
                                            menuEntity.titleColor = 0xff000000;
                                            menuEntity.title = (CharSequence) list_name.get(i);
                                            list.add(menuEntity);
                                        }
                                        // SweetSheet 控件,根据 rl 确认位置
                                        mSweetSheet = new SweetSheet(rl);
                                        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
                                        mSweetSheet.setMenuList(list);
                                        //根据设置不同的 Delegate 来显示不同的风格.
                                        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
                                        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
                                        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
                                        //设置点击事件
                                        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
                                            @Override
                                            public boolean onItemClick(int position, MenuEntity menuEntity) {
                                                choose_position = position;
                                                //即时改变当前项的颜色
                                                list.get(position).titleColor = 0xff5823ff;
                                                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
                                                type = menuEntity.title.toString();

                                                tv_type.setText(type);
                                                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                                                return true;
                                            }
                                        });

                                        if (!mSweetSheet.isShow()) {
                                            mSweetSheet.toggle();
                                        }
                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), payStypeRsp.getMsg());
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                ShowDialogUtil.showSystemFail(getActivity());
                            } finally {

                            }

                        }
                    });
                }


            }

        });
    }

    /**
     * 请求第3方参数
     */
    private void get3pay() {
        if (null == payStypeRsp) {
            return;
        }
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, SportsKey.INCOME_POST)
                .add(SportsKey.ID, payStypeRsp.getIfo().get(choose_position).getID())
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .add(SportsKey.MONEY, money + "")
                .add(SportsKey.URL, payStypeRsp.getIfo().get(choose_position).getUrl())
                .add(SportsKey.BANK_CODE, payStypeRsp.getIfo().get(choose_position).getBank_code())
                .build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.INCOME_POST)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), getString(R.string.net_error));
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message = response.body().string();
                if (null != getActivity()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.e("=======message===========" + message);
                            payIncomeRsp = gson.fromJson(message, PayIncomeRsp.class);
                            if (null == payIncomeRsp) {
                                ShowDialogUtil.showSystemFail(getActivity());
                                return;
                            }
                            switch (payIncomeRsp.getCode()) {
                                case SportsKey.TYPE_ZERO:
                                    ll_first_view.setVisibility(View.GONE);
                                    mWebView.setVisibility(View.VISIBLE);
                                    String url = payIncomeRsp.getIfo().getUrl() + "?" + payIncomeRsp.getIfo().getParas();

                                    initWebview(url);
                                    break;
                                default:
                                    ll_first_view.setVisibility(View.VISIBLE);
                                    mWebView.setVisibility(View.GONE);
                                    ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), payIncomeRsp.getMsg());
                                    break;
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 初始化webview
     */
    private void initWebview(String url) {
        LogUtil.e("========url======" + url);
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
    public void onDestroy() {
        super.onDestroy();
        ShowDialogUtil.dismissDialogs();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            LogUtil.e("=================软键盘弹起=================");
            ifopen = true;
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            ifopen = false;
            LogUtil.e("=================软键盘guanbi=================");
        }
    }
}
