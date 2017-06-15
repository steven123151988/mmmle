package com.daking.sports.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PayIncomeRsp implements Serializable {

    /**
     * code : 0
     * ifo : {"paras":"uid=dfd37b842f0c3cc2b229ra0&order_amount=101&pa_MP=aadmin&token=88d4d7d97636cc4249faae34f3630476&langx=zh-cn&pay_type=&payid=85&Period=0&partner_id=80060910&key=712EFB0FEDBB51FE8603C257FB1B9A82&p8_Url=http://pay.128365365.tv","url":"http://pay.kaluan.top/payfunction/YinBaoZFSXAli.php"}
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
         * paras : uid=dfd37b842f0c3cc2b229ra0&order_amount=101&pa_MP=aadmin&token=88d4d7d97636cc4249faae34f3630476&langx=zh-cn&pay_type=&payid=85&Period=0&partner_id=80060910&key=712EFB0FEDBB51FE8603C257FB1B9A82&p8_Url=http://pay.128365365.tv
         * url : http://pay.kaluan.top/payfunction/YinBaoZFSXAli.php
         */

        private String paras;
        private String url;

        public String getParas() {
            return paras;
        }

        public void setParas(String paras) {
            this.paras = paras;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
