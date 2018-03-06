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


    /**
     *  根据错误码找到提示信息
     * @param errorCode
     * @return
     */
    public static String getErrorCodeInfo(String errorCode) {
        if (null == errorCode) {
            return "未知错误";
        }
        String info = "";
        boolean needErrorCode = false;
        switch (errorCode) {
            case "2001":
                info = "您的密码输入有误，请重试！";
                break;
            case "2002":
                info = "该用户不存在，请检查后重试！";
                break;
            case "2003":
                needErrorCode = true;
                info = "因网络原因，您的注册未成功，请重试！";
                break;
            case "2004":
                info = "该用户名已被占用，请重新输入！";
                break;
            case "2005":
                info = "该账户已被停用，如有疑问，请联系客服！";
                break;
            case "2006":
                info = "恭喜您，操作成功！";
                break;
            case "2007":
                info = "您的支付密码输入有误，请重新输入！";
                break;
            case "2008":
                info = "用户名必须为字母开头的6~15位字母和数字组合，请重新输入！";
                break;
            case "2009":
                info = "密码必须为6~15位字母和数字组合，请重新输入！";
                break;
            case "2010":
                info = "您的支付密码过于简单，请重新输入！";
                break;
            case "2011":
                info = "禁止使用同名注册，请重新输入！";
                break;
            case "2012":
                info = "请检查您的手机号码格式是否正确，谢谢！";
                break;
            case "2013":
                info = "该账户已被冻结，如有疑问，请联系客服！";
                break;
            case "2014":
                info = "此IP已被注册";
                break;
            case "2018":
                info = "您已很长时间未修改密码，为了您的账号安全，请修改密码";
                break;
            case "3001":
                needErrorCode = true;
                info = "下注内容未开放，请尝试其他游戏！";
                break;
            case "3002":
                needErrorCode = true;
                info = "下注内容未开放，请尝试该游戏的其他玩法！";
                break;
            case "3003":
                needErrorCode = true;
                info = "抱歉，网络原因导致下注未成功，请重新尝试！";
                break;
            case "3004":
                info = "单双玩法一期仅能下注一次，请下期再试！";
                break;
            case "4001":
                info = "登录信息已失效，请重新登录";
                break;
            case "4003":
                info = "系统维护中，请稍后重试";
                break;
            case "5001":
                info = "请检查您的投注内容和金额是否正确！";
                break;
            case "5002":
                needErrorCode = true;
                info = "该游戏正在封盘，可前往其他游戏！";
                break;
            case "5003":
                needErrorCode = true;
                info = "因网络原因，本次投注未成功，请稍后重试！";
                break;
            case "5004":
                info = "您当前余额不足，可立即前往充值！";
                break;
            case "5005":
                info = "请检查您的金额输入是否正确，谢谢！";
                break;
            case "5006":
                info = "您的操作过于频繁，请稍后再试！";
                break;
            case "5007":
                info = "请您在上一笔交易完成后再试，谢谢！";
                break;
            case "5008":
                info = "网络繁忙，请稍后再试！";
                break;
            case "5009":
                info = "您的操作过于频繁，请稍后再试！";
                break;
            case "5010":
                info = "请不要重复提交，谢谢！";
                break;
            case "6001":
                info = "您选择的号码过少，请确认后再进行下注！";
                break;
            case "6002":
                info = "您选择的号码过多，请确认后再进行下注！";
                break;
            case "6003":
                info = "您的下注金额低于最低投注额，请重新输入！";
                break;
            case "6004":
                info = "您的今日提款次数已达上限，请明天再来！";
                break;
            case "60041":
                info = "请您在上一笔交易完成后再试，谢谢！";
                break;
            case "7001":
                needErrorCode = true;
                info = "操作失败，请检查您的网络，并稍后再试！";
                break;
            case "7002":
                needErrorCode = true;
                info = "您的订单查询操作不成功，请刷新后再试！";
                break;
            case "7003":
                needErrorCode = true;
                info = "您的额度转换操作不成功，请刷新后再试！";
                break;
            case "7004":
                info = "您的订单查询不成功，请刷新后再试！";
                break;
            case "7005":
                info = "输入的验证码有误，请重新输入";
                break;
            case "8001":
                info = "输入的手机号码格式有误，请重新输入";
                break;
            case "8002":
                info = "该用户已经绑定过手机号";
                break;
            case "8003":
                needErrorCode = true;
                info = "更新手机号码失败";
                break;
            case "8004":
                info = "手机号未绑定,无法进行此操作";
                break;
            case "8005":
                info = "手机号与当前绑定的不一致";
                break;
            case "8007":
                needErrorCode = true;
                info = "领取彩金失败，数据库错误";
                break;
            case "8008":
                needErrorCode = true;
                info = "您的支付密码修改不成功，请重试！";
                break;
            case "8009":
                needErrorCode = true;
                info = "您的登录密码修改不成功，请重试！";
                break;
            case "8010":
                info = "该网站未开通手机短信功能";
                break;
            case "8011":
                info = "输入的手机号码格式有误，请重新输入";
                break;
            case "8012":
                info = "网络连接有误，请检查您的网络！";
                break;
            case "8013":
                info = "验证码输入错误，请重新输入";
                break;
            case "8014":
                info = "验证码已失效，请重新获取验证码";
                break;
            case "8015":
                needErrorCode = true;
                info = "解绑失败";
                break;
            case "8016":
                info = "该手机号已被绑定过";
                break;
            case "9001":
                needErrorCode = true;
                info = "该手机号已被绑定过";
                break;
            case "9002":
                info = "签到不成功，请稍后重试！";
                break;
            case "9003":
                info = "您的剩余抽奖次数为0，请下次再来！";
                break;
            case "9004":
                info = "您的剩余抽奖次数为0，请下次再来！";
                break;
            case "9005":
                info = "您的账户暂未达到抽奖要求，请留意抽奖规则，谢谢！";
                break;
            case "10011":
            case "10012":
            case "10013":
            case "10014":
            case "10015":
            case "10016":
                needErrorCode = true;
                info = "您的网络连接超时，请稍后再试！";
                break;
            default:
                needErrorCode = false;
        }
        if (needErrorCode) {
            return String.format("%s\n(错误码：%s)", info, errorCode);
        }
        return info;
    }

}
