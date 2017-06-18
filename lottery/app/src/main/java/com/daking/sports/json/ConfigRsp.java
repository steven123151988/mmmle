package com.daking.sports.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/18.
 */

public class ConfigRsp implements Serializable {

    /**
     * code : 0
     * ifo : {"url":"http://sport.api.lebole5.com"}
     */

    private int code;
    private IfoBean ifo;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IfoBean getIfo() {
        return ifo;
    }

    public void setIfo(IfoBean ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * url : http://sport.api.lebole5.com
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}