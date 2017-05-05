package com.daking.sports.base;


import com.daking.sports.view.banner.BaseBannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取图片以便加载到轮播图中
 */
public class GetBannerData {
    public static List<BaseBannerBean> getBannerData(){
        List<BaseBannerBean> list = new ArrayList<BaseBannerBean>();
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group5/M00/5B/0C/wKgBfVXdYkqAEzl0AAL6ZFMAdKk401.jpg"));
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group6/M00/98/E9/wKgBjVXdGPiAUmMHAALfY_C7_7U637.jpg"));
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group6/M00/96/F7/wKgBjVXbxnCABW_iAAKLH0qKKXo870.jpg"));
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group5/M00/5B/0C/wKgBfVXdYkqAEzl0AAL6ZFMAdKk401.jpg"));
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group6/M00/98/E9/wKgBjVXdGPiAUmMHAALfY_C7_7U637.jpg"));
        return list;
    }
}
