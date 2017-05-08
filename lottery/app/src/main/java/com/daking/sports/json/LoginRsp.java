package com.daking.sports.json;

/**
 * Created by 18 on 2017/5/8.
 */

public class LoginRsp {


    /**
     * code : 0
     * ifo : {"msg":"尊敬的会员：北京时间2017年05月02日起原有的\u201c周周返点\u201d活动更换为\u201c天天返点\u201d详情请您登录至http://hg8868.bz/activity.php点击\u201c天天返点\u201d进行查看！","uid":"7a666bcf0b1f6a57c0b8ra2"}
     */

    private int code;
    private IfoBean ifo;

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
         * msg : 尊敬的会员：北京时间2017年05月02日起原有的“周周返点”活动更换为“天天返点”详情请您登录至http://hg8868.bz/activity.php点击“天天返点”进行查看！
         * uid : 7a666bcf0b1f6a57c0b8ra2
         */

        private String msg;
        private String uid;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
