package com.daking.sports.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daking.sports.R;
import com.daking.sports.activity.MainActivity;
import com.daking.sports.activity.webview.WebViewActivity;
import com.daking.sports.base.BaseFragment;
import com.daking.sports.base.GetBannerData;
import com.daking.sports.base.SportsId;
import com.daking.sports.base.SportsKey;
import com.daking.sports.base.SportsAPI;
import com.daking.sports.view.banner.BannerBaseView;
import com.daking.sports.view.banner.MainBannerView;
import com.dalong.marqueeview.MarqueeView;
import com.umeng.analytics.MobclickAgent;

/**
 *   购彩面页
 */
public class FirstFragment extends BaseFragment implements View.OnClickListener {
    private BettingFragment bettingFragment;
    private ServiceFragment serviceFragment;
    private Intent  intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        //轮播图
        RelativeLayout bannerContent = (RelativeLayout) view.findViewById(R.id.banner_cont);
        BannerBaseView banner = new MainBannerView(getActivity());
        bannerContent.addView(banner);
        banner.update(GetBannerData.getBannerData());
        //跑马灯的逻辑
        MarqueeView marqueeView = (MarqueeView)view.findViewById(R.id.tv_marquee);
        marqueeView.setFocusable(true);
        marqueeView.requestFocus();
        marqueeView.setText(getResources().getString(R.string.horseRacelamp));//设置文本
        marqueeView.startScroll(); //start
        //四个按钮点击
        view.findViewById(R.id.ll_betting_top).setOnClickListener(this);
        view.findViewById(R.id.ll_reallyperson).setOnClickListener(this);
        view.findViewById(R.id.ll_sports_help).setOnClickListener(this);
        view.findViewById(R.id.ll_service).setOnClickListener(this);
        view.findViewById(R.id.ll_baccarat).setOnClickListener(this);
        view.findViewById(R.id.ll_tigerVSdragon).setOnClickListener(this);
        view.findViewById(R.id.ll_roulette).setOnClickListener(this);
        view.findViewById(R.id.ll_betting_sportd).setOnClickListener(this);
        view.findViewById(R.id.ll_news).setOnClickListener(this);
        ImageView iv_center=(ImageView) view.findViewById(R.id.iv_center);
        iv_center.setVisibility(View.VISIBLE);
        intent=new Intent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FirstFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FirstFragment");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_betting_top: //下注
                gtotoBetting();
            break;
            case R.id.ll_reallyperson: //AG真人视频
                gotoAG();
                break;
            case R.id.ll_sports_help://帮助
                intent=new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(SportsKey.WEBVIEW_TITLE,getResources().getString(R.string.news));
                intent.putExtra(SportsKey.WEBVIEW_URL, SportsAPI.BASE_URL+ SportsAPI.HELP);
                startActivity(intent);
                break;
            case R.id.ll_service:  //客服
                if (null==serviceFragment){
                    serviceFragment=new ServiceFragment();
                }
                ((MainActivity)getActivity()).showFragmentViews(SportsId.TYPE_SIX,serviceFragment);
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
                intent=new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(SportsKey.WEBVIEW_TITLE,getResources().getString(R.string.news));
                intent.putExtra(SportsKey.WEBVIEW_URL, SportsAPI.BASE_URL+ SportsAPI.NEWS);
                startActivity(intent);
                break;
        }
    }

    /**
     * 跳转到下注面页
     */
    private void gtotoBetting() {
        if (null==bettingFragment){
            bettingFragment=new BettingFragment();
        }
        ((MainActivity)getActivity()).showFragmentViews(SportsId.TYPE_TWO,bettingFragment);
    }

    /**
     * 跳转到AG真人视频面页
     */
    private void gotoAG() {
        intent=new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(SportsKey.WEBVIEW_TITLE,getResources().getString(R.string.ag));
        intent.putExtra(SportsKey.WEBVIEW_URL, SportsAPI.BASE_URL+ SportsAPI.AG);
        startActivity(intent);
    }
}