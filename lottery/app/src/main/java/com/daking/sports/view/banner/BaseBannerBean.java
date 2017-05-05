package com.daking.sports.view.banner;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class BaseBannerBean {
    private String url;
    public BaseBannerBean(String url){
        this.url =  url;
    }
    public BaseBannerBean(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
