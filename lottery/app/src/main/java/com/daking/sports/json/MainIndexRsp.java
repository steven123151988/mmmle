package com.daking.sports.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 18 on 2017/5/28.
 */

public class MainIndexRsp  implements Serializable{

    /**
     * notice : 尊敬的会员：您好，由于近期部分地区不法分子猖獗跳转本公司域名，请大家切勿相信并存款 ，如有遇到类似问题请您使用浏览器输入 182.16.22.99 进行登入会员账号 ，谢谢
     * member : {"ID":"12247","Oid":"de1b30ace76e79120dfera7","Online":"0","Language":"zh-cn","UserName":"xiaobao","Money":"0","Money2":"0","Credit":"0","Alias":"测试","contory":"您所在的城市","Sports":"0","Lottery":"0","Points":"0","OpenType":"D","CurType":"RMB","Pay_Type":"1","LineType":"0","Status":"0","Send":"0","BetType":"0","LoginDate":"2017-06-18","LoginTime":"2017-06-18 21:41:08","OnlineTime":"2017-06-18 21:41:08","LogoutTime":"2017-06-18 04:27:21","AddDate":"2017-03-12 01:34:59","EditDate":"2017-03-12","LoginIP":"122.55.4.242","Url":".http://bet365test.lebole5.com/","Agents":"daa888","World":"caa888","Corprator":"baa888","Super":"aaa888","Admin":"raadmin888","Bank_Address":"昆仑银行","Bank_Account":"9999999999999999999","bank":"昆仑银行","E_Mail":"马尼拉","Phone":"","Notes":"","Withdrawal_Passwd":"0000","Address":"","Reg":"1","ratio":"1","FT_R_Bet":"500000","FT_R_Scene":"500000","FT_P_Bet":"500000","FT_P_Scene":"500000","BK_R_Bet":"500000","BK_R_Scene":"500000","BK_P_Bet":"500000","BK_P_Scene":"500000","BS_R_Bet":"500000","BS_R_Scene":"500000","BS_P_Bet":"500000","BS_P_Scene":"500000","TN_R_Bet":"500000","TN_R_Scene":"500000","TN_P_Bet":"500000","TN_P_Scene":"500000","VB_R_Bet":"500000","VB_R_Scene":"500000","VB_P_Bet":"500000","VB_P_Scene":"500000","OP_R_Bet":"500000","OP_R_Scene":"500000","OP_P_Bet":"500000","OP_P_Scene":"500000","FS_FS_Bet":"50000","FS_FS_Scene":"500000","md5psw":"c294db97b3ca9115fe875aeb9e488c67","ctime":"0","ttime":"0","cmoney":"0.00","tmoney":"0.00","level_id":"0","agopen":"0","agpan":"C39A","cpopen":"0","gd_cpopen":"0","mgopen":"0","blacklist":"0","ptopen":"1","FT_RE_Scene":"500000","AliPayOpen":"0","WeiPayOpen":"0"}
     * slide : [{"img":"http://hg0909.com/Public/images/ad1.jpg","url":"http://hg0909.com/index.php/Help/promotion11?act=app"},{"img":"http://hg0909.com/Public/images/ad2.jpg","url":"http://hg0909.com/index.php/Help/promotion31?act=app"},{"img":"http://hg0909.com/Public/images/ad3.jpg","url":"http://hg0909.com/index.php/Help/promotion32?act=app"}]
     * service_url : https://chat.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=588188&configID=49151&jid=8032204814&s=1
     * help_url : http://hg0909.com/index.php/Help
     * company_url : http://hg0909.com/index.php/Bank/listPay
     * code : 0
     * ifo : {"MID":"2783478","M_Start":"2017-06-18 20:00:00","M_League":"哥伦比亚甲组联赛-附加赛","MB_Team":"国家体育会","TG_Team":"卡利体育会","M_LetB_RB":"0 / 0.5","M_LetB":"1 / 1.5","MB_Ball":"5","TG_Ball":"1","MB_Win_Rate":"1.39","MB_Win_Rate_RB":"0","TG_Win_Rate":"6.1","TG_Win_Rate_RB":"0","mpid":"57409"}
     */

    private String notice;
    private MemberBean member;
    private String service_url;
    private String help_url;
    private String company_url;
    private int code;
    private IfoBean ifo;
    private List<SlideBean> slide;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public String getService_url() {
        return service_url;
    }

    public void setService_url(String service_url) {
        this.service_url = service_url;
    }

    public String getHelp_url() {
        return help_url;
    }

    public void setHelp_url(String help_url) {
        this.help_url = help_url;
    }

    public String getCompany_url() {
        return company_url;
    }

    public void setCompany_url(String company_url) {
        this.company_url = company_url;
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

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public static class MemberBean {
        /**
         * ID : 12247
         * Oid : de1b30ace76e79120dfera7
         * Online : 0
         * Language : zh-cn
         * UserName : xiaobao
         * Money : 0
         * Money2 : 0
         * Credit : 0
         * Alias : 测试
         * contory : 您所在的城市
         * Sports : 0
         * Lottery : 0
         * Points : 0
         * OpenType : D
         * CurType : RMB
         * Pay_Type : 1
         * LineType : 0
         * Status : 0
         * Send : 0
         * BetType : 0
         * LoginDate : 2017-06-18
         * LoginTime : 2017-06-18 21:41:08
         * OnlineTime : 2017-06-18 21:41:08
         * LogoutTime : 2017-06-18 04:27:21
         * AddDate : 2017-03-12 01:34:59
         * EditDate : 2017-03-12
         * LoginIP : 122.55.4.242
         * Url : .http://bet365test.lebole5.com/
         * Agents : daa888
         * World : caa888
         * Corprator : baa888
         * Super : aaa888
         * Admin : raadmin888
         * Bank_Address : 昆仑银行
         * Bank_Account : 9999999999999999999
         * bank : 昆仑银行
         * E_Mail : 马尼拉
         * Phone :
         * Notes :
         * Withdrawal_Passwd : 0000
         * Address :
         * Reg : 1
         * ratio : 1
         * FT_R_Bet : 500000
         * FT_R_Scene : 500000
         * FT_P_Bet : 500000
         * FT_P_Scene : 500000
         * BK_R_Bet : 500000
         * BK_R_Scene : 500000
         * BK_P_Bet : 500000
         * BK_P_Scene : 500000
         * BS_R_Bet : 500000
         * BS_R_Scene : 500000
         * BS_P_Bet : 500000
         * BS_P_Scene : 500000
         * TN_R_Bet : 500000
         * TN_R_Scene : 500000
         * TN_P_Bet : 500000
         * TN_P_Scene : 500000
         * VB_R_Bet : 500000
         * VB_R_Scene : 500000
         * VB_P_Bet : 500000
         * VB_P_Scene : 500000
         * OP_R_Bet : 500000
         * OP_R_Scene : 500000
         * OP_P_Bet : 500000
         * OP_P_Scene : 500000
         * FS_FS_Bet : 50000
         * FS_FS_Scene : 500000
         * md5psw : c294db97b3ca9115fe875aeb9e488c67
         * ctime : 0
         * ttime : 0
         * cmoney : 0.00
         * tmoney : 0.00
         * level_id : 0
         * agopen : 0
         * agpan : C39A
         * cpopen : 0
         * gd_cpopen : 0
         * mgopen : 0
         * blacklist : 0
         * ptopen : 1
         * FT_RE_Scene : 500000
         * AliPayOpen : 0
         * WeiPayOpen : 0
         */

        private String ID;
        private String Oid;
        private String Online;
        private String Language;
        private String UserName;
        private String Money;
        private String Money2;
        private String Credit;
        private String Alias;
        private String contory;
        private String Sports;
        private String Lottery;
        private String Points;
        private String OpenType;
        private String CurType;
        private String Pay_Type;
        private String LineType;
        private String Status;
        private String Send;
        private String BetType;
        private String LoginDate;
        private String LoginTime;
        private String OnlineTime;
        private String LogoutTime;
        private String AddDate;
        private String EditDate;
        private String LoginIP;
        private String Url;
        private String Agents;
        private String World;
        private String Corprator;
        private String Super;
        private String Admin;
        private String Bank_Address;
        private String Bank_Account;
        private String bank;
        private String E_Mail;
        private String Phone;
        private String Notes;
        private String Withdrawal_Passwd;
        private String Address;
        private String Reg;
        private String ratio;
        private String FT_R_Bet;
        private String FT_R_Scene;
        private String FT_P_Bet;
        private String FT_P_Scene;
        private String BK_R_Bet;
        private String BK_R_Scene;
        private String BK_P_Bet;
        private String BK_P_Scene;
        private String BS_R_Bet;
        private String BS_R_Scene;
        private String BS_P_Bet;
        private String BS_P_Scene;
        private String TN_R_Bet;
        private String TN_R_Scene;
        private String TN_P_Bet;
        private String TN_P_Scene;
        private String VB_R_Bet;
        private String VB_R_Scene;
        private String VB_P_Bet;
        private String VB_P_Scene;
        private String OP_R_Bet;
        private String OP_R_Scene;
        private String OP_P_Bet;
        private String OP_P_Scene;
        private String FS_FS_Bet;
        private String FS_FS_Scene;
        private String md5psw;
        private String ctime;
        private String ttime;
        private String cmoney;
        private String tmoney;
        private String level_id;
        private String agopen;
        private String agpan;
        private String cpopen;
        private String gd_cpopen;
        private String mgopen;
        private String blacklist;
        private String ptopen;
        private String FT_RE_Scene;
        private String AliPayOpen;
        private String WeiPayOpen;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getOid() {
            return Oid;
        }

        public void setOid(String Oid) {
            this.Oid = Oid;
        }

        public String getOnline() {
            return Online;
        }

        public void setOnline(String Online) {
            this.Online = Online;
        }

        public String getLanguage() {
            return Language;
        }

        public void setLanguage(String Language) {
            this.Language = Language;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getMoney() {
            return Money;
        }

        public void setMoney(String Money) {
            this.Money = Money;
        }

        public String getMoney2() {
            return Money2;
        }

        public void setMoney2(String Money2) {
            this.Money2 = Money2;
        }

        public String getCredit() {
            return Credit;
        }

        public void setCredit(String Credit) {
            this.Credit = Credit;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String Alias) {
            this.Alias = Alias;
        }

        public String getContory() {
            return contory;
        }

        public void setContory(String contory) {
            this.contory = contory;
        }

        public String getSports() {
            return Sports;
        }

        public void setSports(String Sports) {
            this.Sports = Sports;
        }

        public String getLottery() {
            return Lottery;
        }

        public void setLottery(String Lottery) {
            this.Lottery = Lottery;
        }

        public String getPoints() {
            return Points;
        }

        public void setPoints(String Points) {
            this.Points = Points;
        }

        public String getOpenType() {
            return OpenType;
        }

        public void setOpenType(String OpenType) {
            this.OpenType = OpenType;
        }

        public String getCurType() {
            return CurType;
        }

        public void setCurType(String CurType) {
            this.CurType = CurType;
        }

        public String getPay_Type() {
            return Pay_Type;
        }

        public void setPay_Type(String Pay_Type) {
            this.Pay_Type = Pay_Type;
        }

        public String getLineType() {
            return LineType;
        }

        public void setLineType(String LineType) {
            this.LineType = LineType;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getSend() {
            return Send;
        }

        public void setSend(String Send) {
            this.Send = Send;
        }

        public String getBetType() {
            return BetType;
        }

        public void setBetType(String BetType) {
            this.BetType = BetType;
        }

        public String getLoginDate() {
            return LoginDate;
        }

        public void setLoginDate(String LoginDate) {
            this.LoginDate = LoginDate;
        }

        public String getLoginTime() {
            return LoginTime;
        }

        public void setLoginTime(String LoginTime) {
            this.LoginTime = LoginTime;
        }

        public String getOnlineTime() {
            return OnlineTime;
        }

        public void setOnlineTime(String OnlineTime) {
            this.OnlineTime = OnlineTime;
        }

        public String getLogoutTime() {
            return LogoutTime;
        }

        public void setLogoutTime(String LogoutTime) {
            this.LogoutTime = LogoutTime;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String AddDate) {
            this.AddDate = AddDate;
        }

        public String getEditDate() {
            return EditDate;
        }

        public void setEditDate(String EditDate) {
            this.EditDate = EditDate;
        }

        public String getLoginIP() {
            return LoginIP;
        }

        public void setLoginIP(String LoginIP) {
            this.LoginIP = LoginIP;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public String getAgents() {
            return Agents;
        }

        public void setAgents(String Agents) {
            this.Agents = Agents;
        }

        public String getWorld() {
            return World;
        }

        public void setWorld(String World) {
            this.World = World;
        }

        public String getCorprator() {
            return Corprator;
        }

        public void setCorprator(String Corprator) {
            this.Corprator = Corprator;
        }

        public String getSuper() {
            return Super;
        }

        public void setSuper(String Super) {
            this.Super = Super;
        }

        public String getAdmin() {
            return Admin;
        }

        public void setAdmin(String Admin) {
            this.Admin = Admin;
        }

        public String getBank_Address() {
            return Bank_Address;
        }

        public void setBank_Address(String Bank_Address) {
            this.Bank_Address = Bank_Address;
        }

        public String getBank_Account() {
            return Bank_Account;
        }

        public void setBank_Account(String Bank_Account) {
            this.Bank_Account = Bank_Account;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getE_Mail() {
            return E_Mail;
        }

        public void setE_Mail(String E_Mail) {
            this.E_Mail = E_Mail;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String Notes) {
            this.Notes = Notes;
        }

        public String getWithdrawal_Passwd() {
            return Withdrawal_Passwd;
        }

        public void setWithdrawal_Passwd(String Withdrawal_Passwd) {
            this.Withdrawal_Passwd = Withdrawal_Passwd;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getReg() {
            return Reg;
        }

        public void setReg(String Reg) {
            this.Reg = Reg;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getFT_R_Bet() {
            return FT_R_Bet;
        }

        public void setFT_R_Bet(String FT_R_Bet) {
            this.FT_R_Bet = FT_R_Bet;
        }

        public String getFT_R_Scene() {
            return FT_R_Scene;
        }

        public void setFT_R_Scene(String FT_R_Scene) {
            this.FT_R_Scene = FT_R_Scene;
        }

        public String getFT_P_Bet() {
            return FT_P_Bet;
        }

        public void setFT_P_Bet(String FT_P_Bet) {
            this.FT_P_Bet = FT_P_Bet;
        }

        public String getFT_P_Scene() {
            return FT_P_Scene;
        }

        public void setFT_P_Scene(String FT_P_Scene) {
            this.FT_P_Scene = FT_P_Scene;
        }

        public String getBK_R_Bet() {
            return BK_R_Bet;
        }

        public void setBK_R_Bet(String BK_R_Bet) {
            this.BK_R_Bet = BK_R_Bet;
        }

        public String getBK_R_Scene() {
            return BK_R_Scene;
        }

        public void setBK_R_Scene(String BK_R_Scene) {
            this.BK_R_Scene = BK_R_Scene;
        }

        public String getBK_P_Bet() {
            return BK_P_Bet;
        }

        public void setBK_P_Bet(String BK_P_Bet) {
            this.BK_P_Bet = BK_P_Bet;
        }

        public String getBK_P_Scene() {
            return BK_P_Scene;
        }

        public void setBK_P_Scene(String BK_P_Scene) {
            this.BK_P_Scene = BK_P_Scene;
        }

        public String getBS_R_Bet() {
            return BS_R_Bet;
        }

        public void setBS_R_Bet(String BS_R_Bet) {
            this.BS_R_Bet = BS_R_Bet;
        }

        public String getBS_R_Scene() {
            return BS_R_Scene;
        }

        public void setBS_R_Scene(String BS_R_Scene) {
            this.BS_R_Scene = BS_R_Scene;
        }

        public String getBS_P_Bet() {
            return BS_P_Bet;
        }

        public void setBS_P_Bet(String BS_P_Bet) {
            this.BS_P_Bet = BS_P_Bet;
        }

        public String getBS_P_Scene() {
            return BS_P_Scene;
        }

        public void setBS_P_Scene(String BS_P_Scene) {
            this.BS_P_Scene = BS_P_Scene;
        }

        public String getTN_R_Bet() {
            return TN_R_Bet;
        }

        public void setTN_R_Bet(String TN_R_Bet) {
            this.TN_R_Bet = TN_R_Bet;
        }

        public String getTN_R_Scene() {
            return TN_R_Scene;
        }

        public void setTN_R_Scene(String TN_R_Scene) {
            this.TN_R_Scene = TN_R_Scene;
        }

        public String getTN_P_Bet() {
            return TN_P_Bet;
        }

        public void setTN_P_Bet(String TN_P_Bet) {
            this.TN_P_Bet = TN_P_Bet;
        }

        public String getTN_P_Scene() {
            return TN_P_Scene;
        }

        public void setTN_P_Scene(String TN_P_Scene) {
            this.TN_P_Scene = TN_P_Scene;
        }

        public String getVB_R_Bet() {
            return VB_R_Bet;
        }

        public void setVB_R_Bet(String VB_R_Bet) {
            this.VB_R_Bet = VB_R_Bet;
        }

        public String getVB_R_Scene() {
            return VB_R_Scene;
        }

        public void setVB_R_Scene(String VB_R_Scene) {
            this.VB_R_Scene = VB_R_Scene;
        }

        public String getVB_P_Bet() {
            return VB_P_Bet;
        }

        public void setVB_P_Bet(String VB_P_Bet) {
            this.VB_P_Bet = VB_P_Bet;
        }

        public String getVB_P_Scene() {
            return VB_P_Scene;
        }

        public void setVB_P_Scene(String VB_P_Scene) {
            this.VB_P_Scene = VB_P_Scene;
        }

        public String getOP_R_Bet() {
            return OP_R_Bet;
        }

        public void setOP_R_Bet(String OP_R_Bet) {
            this.OP_R_Bet = OP_R_Bet;
        }

        public String getOP_R_Scene() {
            return OP_R_Scene;
        }

        public void setOP_R_Scene(String OP_R_Scene) {
            this.OP_R_Scene = OP_R_Scene;
        }

        public String getOP_P_Bet() {
            return OP_P_Bet;
        }

        public void setOP_P_Bet(String OP_P_Bet) {
            this.OP_P_Bet = OP_P_Bet;
        }

        public String getOP_P_Scene() {
            return OP_P_Scene;
        }

        public void setOP_P_Scene(String OP_P_Scene) {
            this.OP_P_Scene = OP_P_Scene;
        }

        public String getFS_FS_Bet() {
            return FS_FS_Bet;
        }

        public void setFS_FS_Bet(String FS_FS_Bet) {
            this.FS_FS_Bet = FS_FS_Bet;
        }

        public String getFS_FS_Scene() {
            return FS_FS_Scene;
        }

        public void setFS_FS_Scene(String FS_FS_Scene) {
            this.FS_FS_Scene = FS_FS_Scene;
        }

        public String getMd5psw() {
            return md5psw;
        }

        public void setMd5psw(String md5psw) {
            this.md5psw = md5psw;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTtime() {
            return ttime;
        }

        public void setTtime(String ttime) {
            this.ttime = ttime;
        }

        public String getCmoney() {
            return cmoney;
        }

        public void setCmoney(String cmoney) {
            this.cmoney = cmoney;
        }

        public String getTmoney() {
            return tmoney;
        }

        public void setTmoney(String tmoney) {
            this.tmoney = tmoney;
        }

        public String getLevel_id() {
            return level_id;
        }

        public void setLevel_id(String level_id) {
            this.level_id = level_id;
        }

        public String getAgopen() {
            return agopen;
        }

        public void setAgopen(String agopen) {
            this.agopen = agopen;
        }

        public String getAgpan() {
            return agpan;
        }

        public void setAgpan(String agpan) {
            this.agpan = agpan;
        }

        public String getCpopen() {
            return cpopen;
        }

        public void setCpopen(String cpopen) {
            this.cpopen = cpopen;
        }

        public String getGd_cpopen() {
            return gd_cpopen;
        }

        public void setGd_cpopen(String gd_cpopen) {
            this.gd_cpopen = gd_cpopen;
        }

        public String getMgopen() {
            return mgopen;
        }

        public void setMgopen(String mgopen) {
            this.mgopen = mgopen;
        }

        public String getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(String blacklist) {
            this.blacklist = blacklist;
        }

        public String getPtopen() {
            return ptopen;
        }

        public void setPtopen(String ptopen) {
            this.ptopen = ptopen;
        }

        public String getFT_RE_Scene() {
            return FT_RE_Scene;
        }

        public void setFT_RE_Scene(String FT_RE_Scene) {
            this.FT_RE_Scene = FT_RE_Scene;
        }

        public String getAliPayOpen() {
            return AliPayOpen;
        }

        public void setAliPayOpen(String AliPayOpen) {
            this.AliPayOpen = AliPayOpen;
        }

        public String getWeiPayOpen() {
            return WeiPayOpen;
        }

        public void setWeiPayOpen(String WeiPayOpen) {
            this.WeiPayOpen = WeiPayOpen;
        }
    }

    public static class IfoBean {
        /**
         * MID : 2783478
         * M_Start : 2017-06-18 20:00:00
         * M_League : 哥伦比亚甲组联赛-附加赛
         * MB_Team : 国家体育会
         * TG_Team : 卡利体育会
         * M_LetB_RB : 0 / 0.5
         * M_LetB : 1 / 1.5
         * MB_Ball : 5
         * TG_Ball : 1
         * MB_Win_Rate : 1.39
         * MB_Win_Rate_RB : 0
         * TG_Win_Rate : 6.1
         * TG_Win_Rate_RB : 0
         * mpid : 57409
         */

        private String MID;
        private String M_Start;
        private String M_League;
        private String MB_Team;
        private String TG_Team;
        private String M_LetB_RB;
        private String M_LetB;
        private String MB_Ball;
        private String TG_Ball;
        private String MB_Win_Rate;
        private String MB_Win_Rate_RB;
        private String TG_Win_Rate;
        private String TG_Win_Rate_RB;
        private String mpid;

        public String getMID() {
            return MID;
        }

        public void setMID(String MID) {
            this.MID = MID;
        }

        public String getM_Start() {
            return M_Start;
        }

        public void setM_Start(String M_Start) {
            this.M_Start = M_Start;
        }

        public String getM_League() {
            return M_League;
        }

        public void setM_League(String M_League) {
            this.M_League = M_League;
        }

        public String getMB_Team() {
            return MB_Team;
        }

        public void setMB_Team(String MB_Team) {
            this.MB_Team = MB_Team;
        }

        public String getTG_Team() {
            return TG_Team;
        }

        public void setTG_Team(String TG_Team) {
            this.TG_Team = TG_Team;
        }

        public String getM_LetB_RB() {
            return M_LetB_RB;
        }

        public void setM_LetB_RB(String M_LetB_RB) {
            this.M_LetB_RB = M_LetB_RB;
        }

        public String getM_LetB() {
            return M_LetB;
        }

        public void setM_LetB(String M_LetB) {
            this.M_LetB = M_LetB;
        }

        public String getMB_Ball() {
            return MB_Ball;
        }

        public void setMB_Ball(String MB_Ball) {
            this.MB_Ball = MB_Ball;
        }

        public String getTG_Ball() {
            return TG_Ball;
        }

        public void setTG_Ball(String TG_Ball) {
            this.TG_Ball = TG_Ball;
        }

        public String getMB_Win_Rate() {
            return MB_Win_Rate;
        }

        public void setMB_Win_Rate(String MB_Win_Rate) {
            this.MB_Win_Rate = MB_Win_Rate;
        }

        public String getMB_Win_Rate_RB() {
            return MB_Win_Rate_RB;
        }

        public void setMB_Win_Rate_RB(String MB_Win_Rate_RB) {
            this.MB_Win_Rate_RB = MB_Win_Rate_RB;
        }

        public String getTG_Win_Rate() {
            return TG_Win_Rate;
        }

        public void setTG_Win_Rate(String TG_Win_Rate) {
            this.TG_Win_Rate = TG_Win_Rate;
        }

        public String getTG_Win_Rate_RB() {
            return TG_Win_Rate_RB;
        }

        public void setTG_Win_Rate_RB(String TG_Win_Rate_RB) {
            this.TG_Win_Rate_RB = TG_Win_Rate_RB;
        }

        public String getMpid() {
            return mpid;
        }

        public void setMpid(String mpid) {
            this.mpid = mpid;
        }
    }

    public static class SlideBean {
        /**
         * img : http://hg0909.com/Public/images/ad1.jpg
         * url : http://hg0909.com/index.php/Help/promotion11?act=app
         */

        private String img;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
