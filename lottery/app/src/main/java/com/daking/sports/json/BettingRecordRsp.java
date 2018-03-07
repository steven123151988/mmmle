package com.daking.sports.json;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.下注记录
 */

public class BettingRecordRsp extends BaseModel {


    /**
     * ball : football
     * pnums : 10
     * total_nums : 11
     * pages : 2
     * cpage : 1
     * code : 0
     * ifo : [{"BetTime":"06月12日,01:38:38","ID":"OU58661008","BetType":"独赢","Middle":{"leag":"巴西甲组联赛","mid":"[10106]vs[10105]","team":"甘美奥RS  VS.  巴海亚BA","rate":"甘美奥RS @ 1.45"},"BetScore":"11","Gwin":"4.95","Status":"未结算"},{"BetTime":"06月12日,01:31:43","ID":"OU58660947","BetType":"大小","Middle":{"leag":"瑞典超级甲组联赛","mid":"[10128]vs[10127]","team":"希尔星堡 -角球数  VS.  布诺马博亚纳 -角球数","rate":"大  10.5 @ 0.92"},"BetScore":"11","Gwin":"10.12","Status":"未结算"},{"BetTime":"06月12日,01:31:24","ID":"OU58660930","BetType":"独赢","Middle":{"leag":"南韩K挑战联赛","mid":"[10154]vs[10153]","team":"城南足球俱乐部  VS.  牙山木槿花","rate":"城南足球俱乐部 @ 2.20"},"BetScore":"11","Gwin":"13.2","Status":"未结算"},{"BetTime":"06月12日,01:15:07","ID":"OU58660764","BetType":"独赢","Middle":{"leag":"南韩K挑战联赛","mid":"[10154]vs[10153]","team":"城南足球俱乐部  VS.  牙山木槿花","rate":"城南足球俱乐部 @ 2.20"},"BetScore":"11","Gwin":"13.2","Status":"未结算"},{"BetTime":"06月12日,01:13:13","ID":"OU58660762","BetType":"独赢","Middle":{"leag":"巴西丙组联赛","mid":"[10294]vs[10293]","team":"康菲安卡SE  VS.  森柏欧MA","rate":"康菲安卡SE @ 2.14"},"BetScore":"11","Gwin":"12.54","Status":"未结算"},{"BetTime":"06月12日,01:06:47","ID":"OU58660679","BetType":"独赢","Middle":{"leag":"澳洲新南威尔斯国家超级联赛","mid":"[10166]vs[10165]","team":"悉尼奥林匹克  VS.  悉尼U21","rate":"和局 @ 4.55"},"BetScore":"11","Gwin":"39.05","Status":"未结算"},{"BetTime":"06月12日,01:05:42","ID":"OU58660675","BetType":"让球","Middle":{"leag":"世界杯2018亚洲外围赛","mid":"[10006]vs[10005]","team":"伊朗  1 / 1.5  乌兹别克","rate":"伊朗 @ 1.51"},"BetScore":"11","Gwin":"16.61","Status":"未结算"},{"BetTime":"06月12日,01:03:05","ID":"OU58660670","BetType":"让球","Middle":{"leag":"世界杯2018亚洲外围赛","mid":"[10006]vs[10005]","team":"伊朗  1 / 1.5  乌兹别克","rate":"伊朗 @ 1.51"},"BetScore":"11","Gwin":"16.61","Status":"未结算"},{"BetTime":"06月12日,01:02:48","ID":"OU58660669","BetType":"让球","Middle":{"leag":"澳洲新南威尔斯国家超级联赛","mid":"[10166]vs[10165]","team":"悉尼奥林匹克  1  悉尼U21","rate":"悉尼奥林匹克 @ 0.82"},"BetScore":"11","Gwin":"9.02","Status":"未结算"},{"BetTime":"06月12日,00:57:07","ID":"OU58660620","BetType":"独赢","Middle":{"leag":"澳洲新南威尔斯国家超级联赛","mid":"[10166]vs[10165]","team":"悉尼奥林匹克  VS.  悉尼U21","rate":"悉尼奥林匹克 @ 1.47"},"BetScore":"11","Gwin":"5.17","Status":"未结算"},{"BetTime":"06月05日,22:47:32","ID":"OU58304223","BetType":"让球","Middle":{"leag":"国际足联联合会杯2017(在俄罗斯)","mid":"[60002]vs[60001]","team":"俄罗斯  1.5  纽西兰","rate":"俄罗斯 @ 1.02"},"BetScore":"10","Gwin":"10.2","Status":"未结算"}]
     */

    private String ball;
    private int pnums;
    private int total_nums;
    private int pages;
    private int cpage;


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

    public void setCpage(int cpage) {
        this.cpage = cpage;
    }

    public List<IfoBean> getIfo() {
        return ifo;
    }

    public void setIfo(List<IfoBean> ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * BetTime : 06月12日,01:38:38
         * ID : OU58661008
         * BetType : 独赢
         * Middle : {"leag":"巴西甲组联赛","mid":"[10106]vs[10105]","team":"甘美奥RS  VS.  巴海亚BA","rate":"甘美奥RS @ 1.45"}
         * BetScore : 11
         * Gwin : 4.95
         * Status : 未结算
         */

        private String BetTime;
        private String ID;
        private String BetType;
        private MiddleBean Middle;
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

        public MiddleBean getMiddle() {
            return Middle;
        }

        public void setMiddle(MiddleBean Middle) {
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

        public static class MiddleBean {
            /**
             * leag : 巴西甲组联赛
             * mid : [10106]vs[10105]
             * team : 甘美奥RS  VS.  巴海亚BA
             * rate : 甘美奥RS @ 1.45
             */

            private String leag;
            private String mid;
            private String team;
            private String rate;

            public String getLeag() {
                return leag;
            }

            public void setLeag(String leag) {
                this.leag = leag;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }
        }
    }
}
