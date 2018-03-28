package com.daking.sports.util;

import java.math.BigDecimal;

public class UserModel {


    /**
     * id : 160
     * sessionId : F0A13E9D1456EB3B1FA205261112C4C7
     * username : yuci233
     * realname : 鱼刺
     * balance : 0.0
     * emailaddress : 中国日本省
     * guest : 是否游客(0游客1会员)
     */

    private String id;
    private String sessionId;
    private String username;
    private BigDecimal balance;
    private String realname;
    private String emailaddress;
    private int guest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBalance() {
        return balance.stripTrailingZeros().toPlainString();
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public boolean isVisitor() {
        return guest == 0;
    }
}
