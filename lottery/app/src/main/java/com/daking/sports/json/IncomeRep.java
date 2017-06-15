package com.daking.sports.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class IncomeRep implements Serializable {

    /**
     * type : deposit
     * cpage : 1
     * total_nums : 3
     * pnums : 20
     * pages : 1
     * code : 0
     * ifo : [{"Date":"2017-04-03 05:05:34","Gold":"6.08","Type":"天天返点","Status":"成功","ID":"717399","Remark":"天天返点"},{"Date":"2017-04-02 05:21:46","Gold":"6.42","Type":"天天返点","Status":"成功","ID":"716483","Remark":"天天返点"},{"Date":"2017-04-01 05:14:16","Gold":"0.8","Type":"天天返点","Status":"成功","ID":"715400","Remark":"天天返点"}]
     */

    private String type;
    private int cpage;
    private int total_nums;
    private int pnums;
    private int pages;
    private int code;
    private List<IfoBean> ifo;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCpage() {
        return cpage;
    }

    public void setCpage(int cpage) {
        this.cpage = cpage;
    }

    public int getTotal_nums() {
        return total_nums;
    }

    public void setTotal_nums(int total_nums) {
        this.total_nums = total_nums;
    }

    public int getPnums() {
        return pnums;
    }

    public void setPnums(int pnums) {
        this.pnums = pnums;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
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
         * Date : 2017-04-03 05:05:34
         * Gold : 6.08
         * Type : 天天返点
         * Status : 成功
         * ID : 717399
         * Remark : 天天返点
         */

        private String Date;
        private String Gold;
        private String Type;
        private String Status;
        private String ID;
        private String Remark;

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getGold() {
            return Gold;
        }

        public void setGold(String Gold) {
            this.Gold = Gold;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
