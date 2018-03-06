package com.daking.sports.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/18.
 */

public class ConfigRsp extends BaseModel {


    /**
     * code : 0
     * ifo : {"base_url":"http://sport.api.lebole5.com"}
     */


    private IfoBean ifo;
//    private String msg;

//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }


    public IfoBean getIfo() {
        return ifo;
    }

    public void setIfo(IfoBean ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * base_url : http://sport.api.lebole5.com
         */

        private String base_url;

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }
    }
}
