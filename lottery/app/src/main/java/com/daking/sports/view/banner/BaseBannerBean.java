package com.daking.sports.view.banner;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class BaseBannerBean {
    private String url;

    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public BaseBannerBean(String url, String img) {
        this.url = url;
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
