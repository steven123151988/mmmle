package com.daking.sports.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.activity.login.LoginActivity;
import com.daking.sports.activity.webview.WebViewActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.SportsKey;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.json.LoginRsp;
import com.daking.sports.json.MainIndexRsp;
import com.daking.sports.util.LogUtil;
import com.daking.sports.util.SharePreferencesUtil;
import com.daking.sports.util.ShowDialogUtil;
import com.daking.sports.view.MarqueeTextView;
import com.daking.sports.view.banner.BannerBaseView;
import com.daking.sports.view.banner.BaseBannerBean;
import com.daking.sports.view.banner.MainBannerView;
import com.dalong.marqueeview.MarqueeView;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

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
 * 购彩面页
 */
public class FirstFragment extends BaseFragment implements View.OnClickListener {
    private BettingFragment bettingFragment;
    private ServiceFragment serviceFragment;
    private MainIndexRsp mainIndexRsp;
    private Intent intent;
    private View view;
    private TextView tv_A, tv_B, tv_C, tv_D, tv_E, tv_F;
    private String message;
    private LoginRsp loginRsp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, null);
        initView();
        return view;
    }

    private void initView() {
        tv_A = (TextView) view.findViewById(R.id.tv_A);
        tv_B = (TextView) view.findViewById(R.id.tv_B);
        tv_C = (TextView) view.findViewById(R.id.tv_C);
        tv_D = (TextView) view.findViewById(R.id.tv_D);
        tv_E = (TextView) view.findViewById(R.id.tv_E);
        tv_F = (TextView) view.findViewById(R.id.tv_F);
        //按钮点击
        view.findViewById(R.id.ll_betting_top).setOnClickListener(this);
        view.findViewById(R.id.ll_reallyperson).setOnClickListener(this);
        view.findViewById(R.id.ll_sports_help).setOnClickListener(this);
        view.findViewById(R.id.ll_service).setOnClickListener(this);
        view.findViewById(R.id.ll_baccarat).setOnClickListener(this);
        view.findViewById(R.id.ll_tigerVSdragon).setOnClickListener(this);
        view.findViewById(R.id.ll_roulette).setOnClickListener(this);
        view.findViewById(R.id.ll_betting_sportd).setOnClickListener(this);
        view.findViewById(R.id.ll_news).setOnClickListener(this);
        intent = new Intent();

    }

    private void initHomeIndex() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "main")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.HOME_INDEX)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("===============initHomeIndex=========" + e);
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
                                LogUtil.e("===============initHomeIndex=========" + message);
                                Gson gson = new Gson();
                                mainIndexRsp = gson.fromJson(message, MainIndexRsp.class);
                                if (null == mainIndexRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }

                                //跑马灯的逻辑
                                runhorseLight(mainIndexRsp.getNotice());
                                switch (mainIndexRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        //轮播图
                                        RelativeLayout bannerContent = (RelativeLayout) view.findViewById(R.id.banner_cont);
                                        BannerBaseView banner = new MainBannerView(getActivity());
                                        bannerContent.addView(banner);

                                        List<BaseBannerBean> list = new ArrayList<BaseBannerBean>();
                                        int size = mainIndexRsp.getSlide().size();
                                        for (int i = 0; i < size; i++) {
                                            list.add(new BaseBannerBean(mainIndexRsp.getSlide().get(i).getUrl(), mainIndexRsp.getSlide().get(i).getImg()));
                                        }
                                        banner.update(list);
                                        //修改UI必须在主线程
                                        tv_A.setText(mainIndexRsp.getIfo().getMB_Win_Rate());
                                        tv_B.setText(mainIndexRsp.getIfo().getMB_Team());
                                        tv_C.setText(mainIndexRsp.getIfo().getM_League());
                                        tv_D.setText(mainIndexRsp.getIfo().getMB_Ball() + " : " + mainIndexRsp.getIfo().getTG_Ball());
                                        tv_E.setText(mainIndexRsp.getIfo().getTG_Win_Rate());
                                        tv_F.setText(mainIndexRsp.getIfo().getTG_Team());
                                        SharePreferencesUtil.addString(getActivity(), SportsKey.ACCOUNT_MONEY, mainIndexRsp.getMember().getMoney());
                                        SportsAPI.HELP=mainIndexRsp.getHelp_url();
                                        SportsAPI.SERVICE_URL=mainIndexRsp.getService_url();
                                        SportsAPI.COMPANY_INCOME_H5=mainIndexRsp.getCompany_url();
                                        break;
                                    case SportsKey.TYPE_NINE:
                                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
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


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FirstFragment");
        //如果因为没登录请求没成功主页数据（轮播图等），在这里再请求一次
        initHomeIndex();
    }

    /**
     * 跑马灯的逻辑
     */
    private void runhorseLight(String message) {
        MarqueeTextView marqueeView = (MarqueeTextView) view.findViewById(R.id.marquee);
        marqueeView.setText(message);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FirstFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_betting_top: //下注
                gtotoBetting();
                break;
            case R.id.ll_reallyperson: //AG真人视频
                gotoAG();
                break;
            case R.id.ll_sports_help://帮助
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(SportsKey.WEBVIEW_TITLE, getResources().getString(R.string.sports_help));
                intent.putExtra(SportsKey.WEBVIEW_URL, SportsAPI.HELP);
                startActivity(intent);
                break;
            case R.id.ll_service:  //客服
                if (null == serviceFragment) {
                    serviceFragment = new ServiceFragment();
                }
                ((MainActivity) getActivity()).showFragmentViews(SportsKey.TYPE_SIX, serviceFragment);
                break;
            case R.id.ll_betting_sportd://点击体育赛事
                gtotoBetting();
                break;
            case R.id.ll_baccarat://百家乐
                gotoAG();
                break;
            case R.id.ll_tigerVSdragon://龙虎斗
                gotoAG();
                break;
            case R.id.ll_roulette://轮盘
                gotoAG();
                break;
            case R.id.ll_news://更多新闻
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(SportsKey.WEBVIEW_TITLE, getResources().getString(R.string.news));
                intent.putExtra(SportsKey.WEBVIEW_URL, SportsAPI.NEWS);
                startActivity(intent);
                break;
        }
    }

    /**
     * 跳转到下注面页
     */
    private void gtotoBetting() {
        ((MainActivity) getActivity()).goBetting(SportsKey.FOOTBALL, "");
        ((MainActivity) getActivity()).showFragmentViews(SportsKey.TYPE_TWO, bettingFragment);
    }

    /**
     * 跳转到AG真人视频面页
     */
    private void gotoAG() {
        RequestBody requestBody = new FormBody.Builder()
                .add(SportsKey.FNNAME, "zrsx")
                .add(SportsKey.UID, SharePreferencesUtil.getString(getActivity(), SportsKey.UID, "0"))
                .build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SportsAPI.BASE_URL + SportsAPI.AG)
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
                                LogUtil.e("===============gotoAG=========" + message);
                                Gson gson = new Gson();
                                loginRsp = gson.fromJson(message, LoginRsp.class);
                                if (null == loginRsp) {
                                    ShowDialogUtil.showSystemFail(getActivity());
                                    return;
                                }
                                switch (loginRsp.getCode()) {
                                    case SportsKey.TYPE_ZERO:
                                        intent = new Intent(getActivity(), WebViewActivity.class);
                                        intent.putExtra(SportsKey.WEBVIEW_TITLE, getResources().getString(R.string.ag));
                                        intent.putExtra(SportsKey.WEBVIEW_URL, loginRsp.getIfo());
                                        startActivity(intent);
                                        break;
                                    default:
                                        ShowDialogUtil.showFailDialog(getActivity(), getString(R.string.sorry), loginRsp.getMsg());
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
}
