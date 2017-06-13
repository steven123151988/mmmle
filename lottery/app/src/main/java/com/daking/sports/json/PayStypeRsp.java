package com.daking.sports.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 18 on 2017/5/17.
 */

public class PayStypeRsp implements Serializable {


    /**
     * code : 0
     * ifo : [{"ID":"88","title":"支付宝支付1","url":"http://pay.kaluan.top/payfunction/YinBaoZFSXAli.php","min":"100","max":"5000","bank_code":"ALIPAY"},{"ID":"88","title":"支付宝支付1","url":"http://pay.kaluan.top/payfunction/YinBaoZFSXAli.php","min":"100","max":"5000","bank_code":"ALIPAY"},{"ID":"84","title":"微信支付3","url":"http://pay.128365365.tv/Beeepay/weixin.php","min":"100","max":"1000","bank_code":"WEIXIN"},{"ID":"85","title":"支付宝支付4","url":"http://pay.128365365.tv/Beeepay/alipay.php","min":"100","max":"1000","bank_code":"ALIPAY"},{"ID":"90","title":"微信支付5","url":"http://pay.kaluan.top/payfunction/YinBaoZFSX.php","min":"100","max":"5000","bank_code":"WEIXIN"}]
     */

    private int code;
    private List<IfoBean> ifo;
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

    public List<IfoBean> getIfo() {
        return ifo;
    }

    public void setIfo(List<IfoBean> ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * ID : 88
         * title : 支付宝支付1
         * url : http://pay.kaluan.top/payfunction/YinBaoZFSXAli.php
         * min : 100
         * max : 5000
         * bank_code : ALIPAY
         */

        private String ID;
        private String title;
        private String url;
        private String min;
        private String max;
        private String bank_code;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }
    }
}
