package com.daking.sports.base;


public class SportsAPI {
    public static String BASE_URL = "http://sport.api.lebole5.com";
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
    public static final String GET_VERSION_ANDROID = "systems/getVersionAndroid";
    public static final String NEWS = "http://hg0909.com/index.php/Help/promotion";
    public static final String AG = "/zrsx/index/show";
    public static String HELP = "http://hg0909.com/index.php/Help";
    public static String COMPANY_INCOME_H5 = "http://hg0909.com/index.php/Bank/listPay";//公司入款说明
    public static String SERVICE_URL = "https://chat.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=588188&configID=49151&jid=8032204814&s=1";

    /**
     * 根据错误码找到提示信息
     *
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
            case "1":
                info = "姓名没输入中文或是没传值.";
                break;
            case "2":
                info = "IP存在异常情况.";
                break;
            case "3":
                info = "帐户已经有人使用，请重新注册.";
                break;
            case "4":
                info = "Keys字段错误参数错误.";
                break;
            case "5":
                info = "网站错误，请联系网站管理员，感谢您的访问.";
                break;
            case "6":
                info = "登出失败！";
                break;
            case "7":
                info = "暂无你所选择的足球赛事盘口，请稍后查看！";
                break;
            case "8":
                info = "暂无你所选择的蓝球赛事盘口，请稍后查看！";
                break;
            case "9":
                info = "身份信息已过期，请重新登录!";
                break;
            case "10":
                info = "暂无赛事盘口，请稍后查看！";
                break;
            case "11":
                info = "未登录.";
                break;
            case "12":
                info = "赛事已关闭！";
                break;
            case "13":
                info = "暂无可下注盘口.";
                break;
            case "14":
                info = "网络错误！";
                break;
            case "15":
                info = "订单重复！";
                break;
            case "16":
                info = "账户余额不足！";
                break;
            case "19":
                info = "暂无投注记录！";
                break;
            case "20":
                info = "用户名格式不正确.";
                break;
            case "21":
                info = "该用户名已经被注册.";
                break;
            case "22":
                info = "您输入的推荐代理不存在.";
                break;
            case "23":
                info = "真实姓名格式不正确，请输入全中文姓名！";
                break;
            case "24":
                info = "密码格式不正确！";
                break;
            case "25":
                info = "操作失败!(数据库操作失败)";
                break;
            case "26":
                info = "原密码格式不正确!";
                break;
            case "27":
                info = "新密码格式不正确！";
                break;
            case "28":
                info = "原密码输入错误！";
                break;
            case "29":
                info = "网络出现故障，密码修改失败！";
                break;
            case "30":
                info = "原提款密码格式不正确！";
                break;
            case "31":
                info = "新提款密码格式不正确！";
                break;
            case "50":
                info = "请选择开户银行！";
                break;
            case "51":
                info = "请填写16位或19位正确数字格式的银行账号！";
                break;
            case "52":
                info = "银行账户信息修改失败！";
                break;
            case "53":
                info = "提款申请失败！原因:你输入的提款密码错误！";
                break;
            case "54":
                info = "提款申请失败！原因:提款金额大于账户资金!";
                break;
            case "55":
                info = "提款操作失败！";
                break;
            case "56":
                info = "请输入有效数字的金额！";
                break;
            case "60":
                info = "暂无公司的入款账号，请选择其它在线支付方式！";
                break;
            case "61":
                info = "暂无此类入款账号，请选择其它在线支付方式！";
                break;
            case "99":
                info = "fnName沒傳值或沒傳對值！";
                break;
            case "1000":
                info = "上面的回传参数msg展示所有体育赛事维护内容.";
                break;
            case "1001":
                info = "上面的回传参数msg展示滚球赛事维护内容.";
                break;
            case "1002":
                info = "上面的回传参数msg展示今日赛事维护内容.";
                break;
            case "10011":
                needErrorCode = true;
                info = "返回数据类型解析错误{JSONException}.";
                break;
            case "10012":
                needErrorCode = true;
                info = "请求连接错误{ConnectException}.";
                break;
            case "10013":
                needErrorCode = true;
                info = "请求权限错误{SSLHandshakeException}.";
                break;
            case "10014":
                needErrorCode = true;
                info = "网络异常{UnknownHostException}.";
                break;
            case "10015":
                needErrorCode = true;
                info = "请求连接超时{SocketTimeoutException}.";
                break;
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
