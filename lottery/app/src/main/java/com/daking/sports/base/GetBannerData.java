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
//        list.add(new BaseBannerBean("http://hg0909.com/index.php/Help/promotion11",1));
//        list.add(new BaseBannerBean("http://hg0909.com/index.php/Help/promotion31",2));
//        list.add(new BaseBannerBean("http://hg0909.com/index.php/Help/promotion32",3));
        return list;
    }
}
