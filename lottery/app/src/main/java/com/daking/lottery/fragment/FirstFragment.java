package com.daking.lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daking.lottery.R;
import com.daking.lottery.base.BaseFragment;
import com.daking.lottery.base.GetBannerData;
import com.daking.lottery.view.banner.BannerBaseView;
import com.daking.lottery.view.banner.MainBannerView;
import com.dalong.marqueeview.MarqueeView;


public class FirstFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvCenter;

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

        mTvCenter= (TextView) view.findViewById(R.id.tv_center);
        mTvCenter.setText(R.string.app_name);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.all:

            break;
        }
    }
}
