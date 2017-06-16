package com.daking.sports.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class CompannyIncomeRsp implements Serializable {

    /**
     * code : 0
     * ifo : [{"ID":"68","Bank_Account":"请联系在线客服","Bank_Address":"三明永安支行营业室","UserName":"邱x添","Bank":"工商银行","DateTime":"","is_alipay":"0","is_wei_pay":"0","we_img":"","alipay_img":"","url":"/pay/payonline/deposit_company_post","min":"100","max":"0"}]
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
         * ID : 68
         * Bank_Account : 请联系在线客服
         * Bank_Address : 三明永安支行营业室
         * UserName : 邱x添
         * Bank : 工商银行
         * DateTime :
         * is_alipay : 0
         * is_wei_pay : 0
         * we_img :
         * alipay_img :
         * url : /pay/payonline/deposit_company_post
         * min : 100
         * max : 0
         */

        private String ID;
        private String Bank_Account;
        private String Bank_Address;
        private String UserName;
        private String Bank;
        private String DateTime;
        private String is_alipay;
        private String is_wei_pay;
        private String we_img;
        private String alipay_img;
        private String url;
        private String min;
        private String max;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getBank_Account() {
            return Bank_Account;
        }

        public void setBank_Account(String Bank_Account) {
            this.Bank_Account = Bank_Account;
        }

        public String getBank_Address() {
            return Bank_Address;
        }

        public void setBank_Address(String Bank_Address) {
            this.Bank_Address = Bank_Address;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getBank() {
            return Bank;
        }

        public void setBank(String Bank) {
            this.Bank = Bank;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public String getIs_alipay() {
            return is_alipay;
        }

        public void setIs_alipay(String is_alipay) {
            this.is_alipay = is_alipay;
        }

        public String getIs_wei_pay() {
            return is_wei_pay;
        }

        public void setIs_wei_pay(String is_wei_pay) {
            this.is_wei_pay = is_wei_pay;
        }

        public String getWe_img() {
            return we_img;
        }

        public void setWe_img(String we_img) {
            this.we_img = we_img;
        }

        public String getAlipay_img() {
            return alipay_img;
        }

        public void setAlipay_img(String alipay_img) {
            this.alipay_img = alipay_img;
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
    }
}
