package com.daking.sports.base;


public class SportsAPI {
    public static  String BASE_URL = "http://sport.api.lebole5.com";
    public static final String CONFIG_INDEX = "/config/index";//请求全局变量
    public static final String REGIST = "/member/register/check_reg";//注册
    public static final String CHECK_USER = "/member/register/chk_user";//检查用户名称
    public static final String LOGIN = "/login/login";//登陆
    public static final String LOGIN_OUT = "/login/loginout";//登出
    public static final String HOME_INDEX = "/home/index"; //主页数据
    public static final String HOME_MENU = "/home/main_menu"; //主页菜单数据
    public static final String MATCH_LIST = "/home/match_list";//赛事盘口资料
    public static final String GET_MATCH = "/home/get_match";//赛事详情
    public static final String GET_ORDER = "/gqp/site_gqp/ajax_order";//请求下注信息
    public static final String FINISH_ORDER = "/gqp/site_gqp/finish_order";//下单结算
    public static final String BET_BETTING = "/member/member/bet_beting";//投注记录
    public static final String BET_HIS = "/member/member/bet_his";// 账户历史
    public static final String CHANGE_PWD = "/member/member/change_pwd";//修改密码
    public static final String GET_PAY_URL = "/pay/payonline/income";//获取支付链接
    public static final String COMPANY_POST = "/pay/payonline/deposit_company_post";//提交公司入款
    public static final String MEM_ONLINE = "/member/member/mem_online";//在线提款
    public static final String MEM_ONLINE_POST = "/member/member/mem_online_post";//在线提款
    public static final String CHANGE_BANK_INFO = "/member/member/chg_info";//添加更改账户
    public static final String MEM_CAPITAL_FLOW = "/member/member/mem_capital_flow";
    public static final String INCOME_POST = "/pay/payonline/income_post";//请求第3方
    public static final String NEWS = "http://hg0909.com/index.php/Help/promotion";
    public static final String AG = "/zrsx/index/show";
    public static  String HELP = "http://hg0909.com/index.php/Help";
    public static  String COMPANY_INCOME_H5 = "http://hg0909.com/index.php/Bank/listPay";//公司入款说明
    public static  String SERVICE_URL = "https://chat.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=588188&configID=49151&jid=8032204814&s=1";
}
