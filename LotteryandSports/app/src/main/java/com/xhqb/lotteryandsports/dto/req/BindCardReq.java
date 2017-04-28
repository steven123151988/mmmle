package com.xhqb.lotteryandsports.dto.req;

import java.io.Serializable;

/**
 * Created by quchuangye on 2017/3/23.
 */

public class BindCardReq implements Serializable {
    private String cardType;
    private String channel;
    private String bankName;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
