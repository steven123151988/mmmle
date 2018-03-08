package com.daking.sports.json;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/13.
 */

public class MemOnlineRsp extends BaseModel {

    /**
     * code : 0
     * ifo : {"UserName":"xiaobao","Money":"0","Alias":"测试","bank":"","Bank_Account":"","Bank_Address":""}
     */


    private IfoBean ifo;


    public IfoBean getIfo() {
        return ifo;
    }

    public void setIfo(IfoBean ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * UserName : xiaobao
         * Money : 0
         * Alias : 测试
         * bank :
         * Bank_Account :
         * Bank_Address :
         */

        private String UserName;
        private String Money;
        private String Alias;
        private String bank;
        private String Bank_Account;
        private String Bank_Address;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getMoney() {
            return Money;
        }

        public void setMoney(String Money) {
            this.Money = Money;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String Alias) {
            this.Alias = Alias;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
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
    }
}
