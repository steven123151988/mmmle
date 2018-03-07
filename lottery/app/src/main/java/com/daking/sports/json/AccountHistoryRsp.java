package com.daking.sports.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class AccountHistoryRsp extends BaseModel {

    /**
     * start_date : 2017-06-05 21:13:17
     * end_date : 2017-06-12 21:13:17
     * code : 0
     * ifo : [{"date":"2017-06-05星期一","vgold":141,"betscore":141,"m_result":160.5},{"date":"2017-06-06星期二","vgold":0,"betscore":10,"m_result":0.9},{"date":"2017-06-07星期三","vgold":10,"betscore":20,"m_result":-10},{"date":"2017-06-08星期四","vgold":0,"betscore":0,"m_result":0},{"date":"2017-06-09星期五","vgold":372,"betscore":504,"m_result":282.01},{"date":"2017-06-10星期六","vgold":136,"betscore":210,"m_result":-72.56},{"date":"2017-06-11星期日","vgold":39,"betscore":77,"m_result":-5.5},{"date":"2017-06-12星期一","vgold":127,"betscore":198,"m_result":-21.29}]
     */

    private String start_date;
    private String end_date;
    private List<IfoBean> ifo;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }


    public List<IfoBean> getIfo() {
        return ifo;
    }

    public void setIfo(List<IfoBean> ifo) {
        this.ifo = ifo;
    }

    public static class IfoBean {
        /**
         * date : 2017-06-05星期一
         * vgold : 141
         * betscore : 141
         * m_result : 160.5
         */

        private String date;
        private int vgold;
        private int betscore;
        private double m_result;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getVgold() {
            return vgold;
        }

        public void setVgold(int vgold) {
            this.vgold = vgold;
        }

        public int getBetscore() {
            return betscore;
        }

        public void setBetscore(int betscore) {
            this.betscore = betscore;
        }

        public double getM_result() {
            return m_result;
        }

        public void setM_result(double m_result) {
            this.m_result = m_result;
        }
    }
}
