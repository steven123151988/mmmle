package com.daking.sports.json;


/**
 * Created by 18 on 2017/5/8.
 */

public class LoginRsp extends BaseModel {


    /**
     * code : 0
     * msg : 尊敬的会员们，本公司因业务需要，暂时停止使用工商银行(朱文香)(王银)农业银行(潘小芳)(杜小琴)，请联系客服查看最新存款账户！
     * ifo : 46f1ddeb482b829ec14bra7
     */

    private String ifo;
    private String backup;


    public String getIfo() {
        return ifo;
    }

    public void setIfo(String ifo) {
        this.ifo = ifo;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }
}
