package com.daking.sports.json;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.下注记录
 */

public class BettingRecordRsp {


    /**
     * ball : football
     * pnums : 10
     * total_nums : 2
     * pages : 1
     * cpage : 1
     * code : 0
     * ifo : [{"BetTime":"06月11日,22:48:07","ID":"OU58660182","BetType":"滚球独赢","Middle":"美国国家足球超级联赛[72616]vs[72615]<br>里弗赛德科拉  <b>VS.  南加利福尼亚  0:0里弗赛德科拉 @ <b>2.64","BetScore":"11","Gwin":"18.04","Status":"未结算"},{"BetTime":"06月05日,22:47:32","ID":"OU58304223","BetType":"让球","Middle":"国际足联联合会杯2017(在俄罗斯)[60002]vs[60001]<br>俄罗斯  <b>1.5  纽西兰俄罗斯 @ <b>1.02","BetScore":"10","Gwin":"10.2","Status":"未结算"}]
     */

    private String ball;
    private int pnums;
    private int total_nums;
    private int pages;
    private int cpage;
    private int code;
    private String msg;
    private List<IfoBean> ifo;

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public int getPnums() {
        return pnums;
    }

    public void setPnums(int pnums) {
        this.pnums = pnums;
    }

    public int getTotal_nums() {
        return total_nums;
    }

    public void setTotal_nums(int total_nums) {
        this.total_nums = total_nums;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCpage() {
        return cpage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCpage(int cpage) {
        this.cpage = cpage;
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
         * BetTime : 06月11日,22:48:07
         * ID : OU58660182
         * BetType : 滚球独赢
         * Middle : 美国国家足球超级联赛[72616]vs[72615]<br>里弗赛德科拉  <b>VS.  南加利福尼亚  0:0里弗赛德科拉 @ <b>2.64
         * BetScore : 11
         * Gwin : 18.04
         * Status : 未结算
         */

        private String BetTime;
        private String ID;
        private String BetType;
        private String Middle;
        private String BetScore;
        private String Gwin;
        private String Status;

        public String getBetTime() {
            return BetTime;
        }

        public void setBetTime(String BetTime) {
            this.BetTime = BetTime;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getBetType() {
            return BetType;
        }

        public void setBetType(String BetType) {
            this.BetType = BetType;
        }

        public String getMiddle() {
            return Middle;
        }

        public void setMiddle(String Middle) {
            this.Middle = Middle;
        }

        public String getBetScore() {
            return BetScore;
        }

        public void setBetScore(String BetScore) {
            this.BetScore = BetScore;
        }

        public String getGwin() {
            return Gwin;
        }

        public void setGwin(String Gwin) {
            this.Gwin = Gwin;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }
    }
}
