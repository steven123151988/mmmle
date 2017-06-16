package com.daking.sports.json;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 18 on 2017/6/8.
 */

public class GetOrderMsgRsp implements Serializable {

    /**
     * code : 0
     * ifo : {"class":"OFT","inballs":"&nbsp;&nbsp;<em class=\"bold\">(0:0)<\/em>","gq":1,"menu":"足球","M_League":"2017世界杯U20(在南韩)","M_menu":"全场 (滚球) - 让球","MB_Team":"委內瑞拉U20","Signs":"<span class=\"radio\">0 / 0.5<\/span>","TG_Team":"乌拉圭 U20","M_Place":"乌拉圭 U20","M_Rate":"0.58","type":"RE","gold":"111","GMIN_SINGLE":"10","gmax":"500,000","ball":"football","str":"gid=2776154&uid=2ae5c820fc6e79120dfera7&type=H&gnum=40286&stong=C","json_paras":"{\"gid\":\"2776154\",\"uid\":\"2ae5c820fc6e79120dfera7\",\"type\":\"H\",\"gnum\":\"40286\",\"stong\":\"C\",\"ball\":\"football\",\"gq\":\"false\",\"stype\":\"RE\",\"token\":\"195442dea2101ac0e1b41e13fac611ea\",\"langx\":\"zh-cn\",\"odd_f_type\":\"H\",\"active\":1,\"line_type\":9,\"concede_r\":1,\"radio_r\":100,\"ioradio_r_h\":\"0.58\",\"gmax_single\":500000,\"gmin_single\":\"10\",\"singlecredit\":\"500000\",\"singleorder\":\"500000\",\"restsinglecredit\":0,\"wagerstotal\":\"500000\",\"restcredit\":\"0\",\"pay_type\":\"1\"}"}
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
         * class : OFT
         * inballs : &nbsp;&nbsp;<em class="bold">(0:0)</em>
         * gq : 1
         * menu : 足球
         * M_League : 2017世界杯U20(在南韩)
         * M_menu : 全场 (滚球) - 让球
         * MB_Team : 委內瑞拉U20
         * Signs : <span class="radio">0 / 0.5</span>
         * TG_Team : 乌拉圭 U20
         * M_Place : 乌拉圭 U20
         * M_Rate : 0.58
         * type : RE
         * gold : 111
         * GMIN_SINGLE : 10
         * gmax : 500,000
         * ball : football
         * str : gid=2776154&uid=2ae5c820fc6e79120dfera7&type=H&gnum=40286&stong=C
         * json_paras : {"gid":"2776154","uid":"2ae5c820fc6e79120dfera7","type":"H","gnum":"40286","stong":"C","ball":"football","gq":"false","stype":"RE","token":"195442dea2101ac0e1b41e13fac611ea","langx":"zh-cn","odd_f_type":"H","active":1,"line_type":9,"concede_r":1,"radio_r":100,"ioradio_r_h":"0.58","gmax_single":500000,"gmin_single":"10","singlecredit":"500000","singleorder":"500000","restsinglecredit":0,"wagerstotal":"500000","restcredit":"0","pay_type":"1"}
         */

        @SerializedName("class")
        private String classX;
        private String inballs;
        private int gq;
        private String menu;
        private String M_League;
        private String M_menu;
        private String MB_Team;
        private String Signs;
        private String TG_Team;
        private String M_Place;
        private String M_Rate;
        private String type;
        private String gold;
        private String GMIN_SINGLE;
        private String gmax;
        private String ball;
        private String str;
        private String json_paras;
        private String token;
        private String Money;

        public String getMoney() {
            return Money;
        }

        public void setMoney(String money) {
            Money = money;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getInballs() {
            return inballs;
        }

        public void setInballs(String inballs) {
            this.inballs = inballs;
        }

        public int getGq() {
            return gq;
        }

        public void setGq(int gq) {
            this.gq = gq;
        }

        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public String getM_League() {
            return M_League;
        }

        public void setM_League(String M_League) {
            this.M_League = M_League;
        }

        public String getM_menu() {
            return M_menu;
        }

        public void setM_menu(String M_menu) {
            this.M_menu = M_menu;
        }

        public String getMB_Team() {
            return MB_Team;
        }

        public void setMB_Team(String MB_Team) {
            this.MB_Team = MB_Team;
        }

        public String getSigns() {
            return Signs;
        }

        public void setSigns(String Signs) {
            this.Signs = Signs;
        }

        public String getTG_Team() {
            return TG_Team;
        }

        public void setTG_Team(String TG_Team) {
            this.TG_Team = TG_Team;
        }

        public String getM_Place() {
            return M_Place;
        }

        public void setM_Place(String M_Place) {
            this.M_Place = M_Place;
        }

        public String getM_Rate() {
            return M_Rate;
        }

        public void setM_Rate(String M_Rate) {
            this.M_Rate = M_Rate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getGMIN_SINGLE() {
            return GMIN_SINGLE;
        }

        public void setGMIN_SINGLE(String GMIN_SINGLE) {
            this.GMIN_SINGLE = GMIN_SINGLE;
        }

        public String getGmax() {
            return gmax;
        }

        public void setGmax(String gmax) {
            this.gmax = gmax;
        }

        public String getBall() {
            return ball;
        }

        public void setBall(String ball) {
            this.ball = ball;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public String getJson_paras() {
            return json_paras;
        }

        public void setJson_paras(String json_paras) {
            this.json_paras = json_paras;
        }
    }
}
