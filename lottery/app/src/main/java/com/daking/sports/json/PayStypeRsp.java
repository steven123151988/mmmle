package com.daking.sports.json;

import java.util.List;

/**
 * Created by 18 on 2017/5/17.
 */

public class PayStypeRsp {

    /**
     * code : 0
     * ifo : [{"dsp":true,"paytype":"outline","dspname":"在线支付","url":"http://pay.mingzc2.com/onlinepay/payonline.php","Period":0,"payid":"55"},{"dsp":true,"paytype":"company","dspname":"公司入款","url":"http://sport.api.lebole5.com/remittance.php","Period":0,"payid":"92,79,77,91"},{"dsp":true,"paytype":"ali","dspname":"支付宝入款","url":"http://sport.api.lebole5.com/alipay.php","Period":0,"payid":"115"}]
     * msg :   尊敬的会员们，本公司因业务需要，暂时停止使用工商银行(朱文香)(王银)农业银行(潘小芳)(杜小琴)（杨籽妤）中国银行（赵瑞红）招商银行（万克宝），若存款到已停止使用的账号，我们将不予受理，有带来不便，敬请谅解，谢谢！
     */

    private int code;
    private String msg;
    private List<IfoBean> ifo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<IfoBean> getIfo() {
        return ifo;
    }

    public void setIfo(List<IfoBean> ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * dsp : true
         * paytype : outline
         * dspname : 在线支付
         * url : http://pay.mingzc2.com/onlinepay/payonline.php
         * Period : 0
         * payid : 55
         */

        private boolean dsp;
        private String paytype;
        private String dspname;
        private String url;
        private int Period;
        private String payid;

        public boolean isDsp() {
            return dsp;
        }

        public void setDsp(boolean dsp) {
            this.dsp = dsp;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getDspname() {
            return dspname;
        }

        public void setDspname(String dspname) {
            this.dspname = dspname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPeriod() {
            return Period;
        }

        public void setPeriod(int Period) {
            this.Period = Period;
        }

        public String getPayid() {
            return payid;
        }

        public void setPayid(String payid) {
            this.payid = payid;
        }
    }
}
