package com.beibeiMajor.web.util.executor;

public interface Constant {
    //登录
    String USER_APP_API_LOGIN = "/login";
    //分页获取积分列表
    String USER_APP_API_INTEGRAL_LIST = "/integral_list";
    //榜单前五
    String USER_APP_API_RANK_LIST = "/rank_list";
    //分页获取比赛记录列表
    String USER_APP_API_RECORD_LIST = "/record_list";
    //双倍
    String USER_APP_API_ADD_DOUBLE = "/add_double_integral";
    //登出
    String USER_APP_API_LOGIN_OUT = "/login_out";

    //获取联赛列表
    String USER_APP_API_LEAGUE_LIST = "/league_list";
    //切换当前默认联赛
    String USER_APP_API_CHANGE_LEAGUE = "/change_league";

    //获取我的信息包括雷达图
    String USER_APP_API_GET_USER_INFO = "/userInfo";
}
