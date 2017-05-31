package com.daking.sports.json;

/**
 * Created by 18 on 2017/5/31.
 */

public class BettingDetailRsp {
    /**
     * code : 9
     * msg : 身份信息已过期，请重新登录！
     */

    private int code;
    private String msg;

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
}
