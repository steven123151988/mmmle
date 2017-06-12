package com.daking.sports.json;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.下注记录
 */

public class BettingRecordRsp {

    /**
     * ball : football
     * code : 0
     * ifo : [{"BetTime":"06月11日,22:48:07","ID":"OU58660182","BetType":"滚球独赢","Middle":"美国国家足球超级联赛<br>[72616]vs[72615]<br>里弗赛德科拉&nbsp;&nbsp;<FONT COLOR=#0000BB><b>VS.<\/b><\/FONT>&nbsp;&nbsp;南加利福尼亚&nbsp;&nbsp;<FONT color=red><b>0:0<\/b><\/FONT><br><FONT color=#cc0000>里弗赛德科拉<\/FONT>&nbsp;@&nbsp;<FONT color=#cc0000><b>2.64<\/b><\/FONT>","BetScore":"11","Gwin":"18.04","Status":"未结算"},{"BetTime":"06月05日,22:47:32","ID":"OU58304223","BetType":"让球","Middle":"国际足联联合会杯2017(在俄罗斯)<br>[60002]vs[60001]<br>俄罗斯&nbsp;&nbsp;<FONT COLOR=#0000BB><b>1.5<\/b><\/FONT>&nbsp;&nbsp;纽西兰<br><FONT color=blue>俄罗斯<\/FONT>&nbsp;@&nbsp;<FONT color=blue><b>1.02<\/b><\/FONT>","BetScore":"10","Gwin":"10.2","Status":"未结算"}]
     */

    private String ball;
    private int code;
    private List<IfoBean> ifo;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
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
         * Middle : 美国国家足球超级联赛<br>[72616]vs[72615]<br>里弗赛德科拉&nbsp;&nbsp;<FONT COLOR=#0000BB><b>VS.</b></FONT>&nbsp;&nbsp;南加利福尼亚&nbsp;&nbsp;<FONT color=red><b>0:0</b></FONT><br><FONT color=#cc0000>里弗赛德科拉</FONT>&nbsp;@&nbsp;<FONT color=#cc0000><b>2.64</b></FONT>
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
