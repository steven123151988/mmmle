package com.daking.sports.json;



public class LotteryVersion extends BaseModel {


    /**
     * code : 0
     * forcedUpdate : no
     * version : 1.4
     * url : api.alcp66.com/mobile/ali.apk
     */

    private String forcedUpdate;
    private String version;
    private String url;

    public String getForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(String forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LotteryVersion{" +
                "forcedUpdate='" + forcedUpdate + '\'' +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
