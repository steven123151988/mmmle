package com.daking.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daking.lottery.R;
import com.daking.lottery.activity.MainActivity;
import com.daking.lottery.activity.WebViewActivity;
import com.daking.lottery.base.BaseFragment;
import com.daking.lottery.base.GetBannerData;
import com.daking.lottery.base.LotteryId;
import com.daking.lottery.base.LotteryKey;
import com.daking.lottery.base.LotteryUrl;
import com.daking.lottery.view.banner.BannerBaseView;
import com.daking.lottery.view.banner.MainBannerView;
import com.dalong.marqueeview.MarqueeView;
import com.umeng.analytics.MobclickAgent;

/**
 *   购彩面页
 */
public class FirstFragment extends BaseFragment implements View.OnClickListener {
    private BettingFragment bettingFragment;
    private ServiceFragment serviceFragment;

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
                if (null==bettingFragment){
                    bettingFragment=new BettingFragment();
                }
                ((MainActivity)getActivity()).showFragmentViews(LotteryId.TYPE_TWO,bettingFragment);
            break;
            case R.id.ll_reallyperson: //真人视频

                break;
            case R.id.ll_sports_help://帮助

                break;
            case R.id.ll_service:  //客服
                if (null==serviceFragment){
                    serviceFragment=new ServiceFragment();
                }
                ((MainActivity)getActivity()).showFragmentViews(LotteryId.TYPE_SIX,serviceFragment);
                break;
            case R.id.ll_betting_sportd://点击体育赛事

                break;
            case R.id.ll_baccarat://百家乐

                break;
            case R.id.ll_tigerVSdragon://龙虎斗

                break;
            case R.id.ll_roulette://轮盘

                break;
            case R.id.ll_news://更多新闻
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(LotteryKey.WEBVIEW_TITLE,getResources().getString(R.string.news));
                intent.putExtra(LotteryKey.WEBVIEW_URL, LotteryUrl.NEWS);
                startActivity(intent);
                break;




        }
    }
}
