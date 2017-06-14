package com.daking.sports.view.banner;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class BaseBannerBean {
    private String url;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BaseBannerBean(String url,int position) {
        this.url = url;
        this.position=position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
