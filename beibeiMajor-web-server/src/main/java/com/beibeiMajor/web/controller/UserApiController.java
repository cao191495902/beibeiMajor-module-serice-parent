package com.beibeiMajor.web.controller;

import com.beibeiMajor.common.annotation.ApiLogin;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.utils.DateUtils;
import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.common.utils.security.Md5Utils;
import com.beibeiMajor.framework.app.service.TokenService;
import com.beibeiMajor.framework.manager.JedisManager;
import com.beibeiMajor.framework.util.ShiroUtils;
import com.beibeiMajor.system.domain.*;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;
import com.beibeiMajor.web.service.OperationInfoToDBService;
import com.beibeiMajor.web.service.ReportInfoService;
import com.beibeiMajor.web.service.dto.MyMatchDetailBean;
import com.beibeiMajor.web.service.dto.TopBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.beibeiMajor.web.util.executor.Constant.*;

/**
 * 主页和用户登录相关处理
 * @author cw
 */
@RestController
@Api(tags = "贝贝Marjor接口")
@RequestMapping("/app-api")
public class UserApiController extends BaseController{


    @Autowired
    ReportInfoService reportInfoService;
    @Autowired
    IWebUserService webUserService;
    @Autowired
    JedisManager jedisManager;
    @Autowired
    IWebDoubleIntegralRecordService iWebDoubleIntegralRecordService;
    @Autowired
    OperationInfoToDBService operationInfoToDBService;
    @Autowired
    TokenService tokenService;

    protected final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    /**
     * @param version
     * @param pf
     * @param deviceInfo
     * @param account
     * @param password
     * @return
     */
    @PostMapping(USER_APP_API_LOGIN)
    @ResponseBody
    @ApiOperation("登录接口")
    public Object ajaxLogin(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号", required = false) Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台", required = false) String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "account", required = false) @ApiParam(value = "用户账号", required = true) String account,
            @RequestParam(value = "password", required = false) @ApiParam(value = "md5加密后的密码", required = true) String password
    ) {

        try {
            if (StringUtils.isEmpty(account) || StringUtils.isEmpty(account)) {
                return AjaxResult.error(4003, "用户名或者密码错误");
            }

            WebUser user = webUserService.selectUserByLoginName(account);
            if (user == null) {
                return AjaxResult.error(4004, "用户不存在");
            }
            if (!user.getPassword().equals(password)) {
                return AjaxResult.error(4005, "用户名或者密码错误");
            }

            String token = Md5Utils.hash(UUID.randomUUID().toString().replaceAll("-", "") + user.getAccountId()).toLowerCase();
            tokenService.addTokenToCache(token, user);

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("accountId", user.getAccountId());
            result.put("nickName", user.getNickName());
            result.put("avatarUrl", user.getAvatar());
            result.put("doubleCount", user.getDoubleIntegralTimes());
            result.put("leagueId",user.getLeagueId());

            WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
            webUserDotaReport.setUserId(user.getAccountId());
            webUserDotaReport.setLeagueId(user.getLeagueId());
            List<WebUserDotaReportPo> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport, 1, 1);
            if (CollectionUtils.isNotEmpty(list)) {
                result.put("rank", list.get(0).getRank());
                result.put("winRate", list.get(0).getWinRate()+"");
                result.put("integral", list.get(0).getIntegral());
            }

            Date startTime = DateUtils.getStartTimeOfDay(new Date());
            Date endTime = DateUtils.getStartTimeOfDay(DateUtils.addDays(startTime, 1));
            //查询当天是否报过名
            WebDoubleIntegralRecord record = iWebDoubleIntegralRecordService.selectByTodayAndAccountId(user.getAccountId(), startTime.getTime() / 1000, endTime.getTime() / 1000);
            result.put("isDouble", record == null ? false : true);

            //获取五边形所需要的数据，近5场比赛的参战能力，多面能力，打钱能力，辅助能力，推塔能力

            //联赛信息
            List<WebLeague> leagueList = webUserService.getLeagueList();
            result.put("leagueList",leagueList);

            return AjaxResult.success(200,result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }


    /**
     * 分页获取积分列表
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping(USER_APP_API_INTEGRAL_LIST)
    @ResponseBody
    @ApiLogin
    @ApiOperation("分页获取积分列表")
    public Object list(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证",required = true)String token,
            @RequestParam(value = "leagueId", required = false) @ApiParam(value = "联赛id,不传使用默认联赛id",required = false)Integer leagueId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1")  @ApiParam(value = "页码") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "页数") int pageSize) {

        try {
            WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
            if (leagueId == null) {
                webUserDotaReport.setLeagueId(leagueId);
            } else {
                WebUser user = tokenService.getChannelByToken(token);
                webUserDotaReport.setLeagueId(user.getLeagueId());
            }
            startPage();
            List<WebUserDotaReportPo> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport, pageNum, pageSize);
            PageHelper.clearPage();
            Map<String, Object> result = new HashMap<>();
            result.put("pages", new PageInfo(list).getPages());
            result.put("list", list);
            return AjaxResult.success(200,result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }

    /**
     * 获取top信息
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     * @return
     */
    @PostMapping(USER_APP_API_RANK_LIST)
    @ResponseBody
    @ApiLogin
    @ApiOperation("获取top信息")
    public Object topList(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "leagueId", required = false) @ApiParam(value = "联赛id,不传使用默认联赛id",required = false)Integer leagueId,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证", required = true) String token) {

        try {
            Map<String, Object> result = new HashMap<>();

            if (leagueId == null) {
                WebUser user = tokenService.getChannelByToken(token);
                leagueId = user.getLeagueId();
            }

            List<TopBean> topList = reportInfoService.statisticsTopInfoList(leagueId);

            List<TopBean> lossList = reportInfoService.statisticsLossInfoList(leagueId);

            result.put("topList", topList);
            result.put("lossList", lossList);
            return AjaxResult.success(200,result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }


    }


    /**
     * 分页获取用户战绩
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping(USER_APP_API_RECORD_LIST)
    @ResponseBody
    @ApiLogin
    @ApiOperation("分页获取用户战绩")
    public Object myRecordList(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "userId", required = false) @ApiParam(value = "用户ID，传谁的就获取谁的，不传获取自己的" )String userId,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证",required = true)String token,
            @RequestParam(value = "leagueId", required = false) @ApiParam(value = "联赛id,不传使用默认联赛id",required = false)Integer leagueId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1")  @ApiParam(value = "页码") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "页数") int pageSize) {


        try {
            WebUser user = tokenService.getChannelByToken(token);

            if (StringUtils.isEmpty(userId) && user != null) {
                userId = user.getAccountId() + "";
            }
            if (leagueId == null) {
                leagueId = user.getLeagueId();
            }
            startPage();
            List<MyMatchDetailBean> list = reportInfoService.getMyRecordList(userId, leagueId, pageNum, pageSize);
            PageHelper.clearPage();
            Map<String, Object> result = new HashMap<>();
            result.put("pages", new PageInfo(list).getPages());
            result.put("list", list);
            return AjaxResult.success(200,result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }

    /**
     * 双倍积分
     *
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     * @return
     */
    @PostMapping(USER_APP_API_ADD_DOUBLE)
    @ResponseBody
    @ApiLogin
    @ApiOperation("双倍积分")
    public Object updateDoubleTimes(@RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
                                    @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
                                    @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
                                    @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证", required = true) String token
    ) {

        try {
            WebUser user = tokenService.getChannelByToken(token);

            if (user == null) {
                return AjaxResult.error(4002, "登录失效");
            }
            //从数据库查询 防止登陆以后 次数做出修改
            user = webUserService.selectWebUserByAccountId(user.getAccountId());

            // 采用redis方式实现 网络波动导致重复插入
            int seconds = 5;
            boolean roll = true;
            int times = 1;
            String ipFrequencyLimitKey = String.format("user:double:times:%s", user.getAccountId());
            long occurTimes = jedisManager.incr(ipFrequencyLimitKey, seconds, roll);
            if (occurTimes > times) {
                return AjaxResult.error("请勿重复操作，5秒后再尝试");
            }

            Date startTime = DateUtils.getStartTimeOfDay(new Date());
            Date endTime = DateUtils.getStartTimeOfDay(DateUtils.addDays(startTime, 1));
            //查询当天是否报过名
            WebDoubleIntegralRecord record = iWebDoubleIntegralRecordService.selectByTodayAndAccountId(user.getAccountId(), startTime.getTime() / 1000, endTime.getTime() / 1000);

            if (record != null) {
                return AjaxResult.error("今日已经双倍");
            }
            //查询报名次数是否够
            WebUser webUser = webUserService.selectWebUserByAccountId(user.getAccountId());
            if (webUser.getDoubleIntegralTimes() <= 0) {
                return AjaxResult.error("双倍次数不够");
            }
            operationInfoToDBService.rollbackDoubleIntegralRecord(user.getAccountId(), -1L, 1, "网站添加双倍",false);
            ShiroUtils.setSysUser(webUser);
            return AjaxResult.success(200,"操作成功");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }
    }

    /**
     *功能描述
     * @author caowei
     * @date 2022/2/16
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
    */
    @PostMapping(USER_APP_API_LOGIN_OUT)
    @ResponseBody
    @ApiLogin
    @ApiOperation("注销用户")
    public Object loginout(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证",required = true)String token){


        try {
            tokenService.cleanTokenUserByToken(token);
            return AjaxResult.success(200,"操作成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }


    /**
     *功能描述
     * @author caowei
     * @date 2022/2/16
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     */
    @PostMapping(USER_APP_API_LEAGUE_LIST)
    @ResponseBody
    @ApiLogin
    @ApiOperation("获取所有联赛列表")
    public Object getLeagueList(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证",required = true)String token){


        try {
            List<WebLeague> leagueList = webUserService.getLeagueList();
            Map<String, Object> result = new HashMap<>();
            result.put("list", leagueList);
            return AjaxResult.success(200, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }

    /**
     *功能描述
     * @author caowei
     * @date 2022/2/16
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     */
    @PostMapping(USER_APP_API_CHANGE_LEAGUE)
    @ResponseBody
    @ApiLogin
    @ApiOperation("切换当前默认联赛")
    public Object changeLeague(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "leagueId", required = false) @ApiParam(value = "联赛id",required = true)Integer leagueId,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证",required = true)String token){


        try {
            WebUser user = tokenService.getChannelByToken(token);
            user.setLeagueId(leagueId);
            tokenService.addTokenToCache(token, user);
            return AjaxResult.success(200,"操作成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }

    /**
     * 功能描述
     *
     * @param version
     * @param pf
     * @param deviceInfo
     * @param token
     * @author caowei
     * @date 2022/2/16
     */
    @PostMapping(USER_APP_API_GET_USER_INFO)
    @ResponseBody
    @ApiLogin
    @ApiOperation("获取个人信息")
    public Object UserInfo(
            @RequestParam(value = "version", required = false) @ApiParam(value = "版本号") Integer version,
            @RequestParam(value = "pf", required = false) @ApiParam(value = "平台") String pf,
            @RequestParam(value = "deviceInfo", required = false) @ApiParam(value = "设备信息") String deviceInfo,
            @RequestParam(value = "leagueId", required = false) @ApiParam(value = "联赛id,不传使用默认联赛id", required = false) Integer leagueId,
            @RequestParam(value = "token", required = false) @ApiParam(value = "登录凭证", required = true) String token) {


        try {
            Map<String, Object> result = new HashMap<>();

            WebUser user = tokenService.getChannelByToken(token);
            if (leagueId == null) {
                leagueId = user.getLeagueId();
            }

            result.put("accountId", user.getAccountId());
            result.put("nickName", user.getNickName());
            result.put("avatarUrl", user.getAvatar());
            result.put("doubleCount", user.getDoubleIntegralTimes());
            result.put("leagueId", user.getLeagueId());

            WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
            webUserDotaReport.setUserId(user.getAccountId());
            webUserDotaReport.setLeagueId(leagueId);
            List<WebUserDotaReportPo> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport, 1, 1);
            if (CollectionUtils.isNotEmpty(list)) {
                result.put("rank", list.get(0).getRank());
                result.put("winRate", list.get(0).getWinRate() + "");
                result.put("integral", list.get(0).getIntegral());
            }
            Date startTime = DateUtils.getStartTimeOfDay(new Date());
            Date endTime = DateUtils.getStartTimeOfDay(DateUtils.addDays(startTime, 1));
            //查询当天是否报过名
            WebDoubleIntegralRecord record = iWebDoubleIntegralRecordService.selectByTodayAndAccountId(user.getAccountId(), startTime.getTime() / 1000, endTime.getTime() / 1000);
            result.put("isDouble", record == null ? false : true);

            //获取五边形所需要的数据，近5场比赛的参战能力,KAD,打钱能力，输出能力，推塔能力

            List<RecentPerform> recentPerformList = reportInfoService.getRecentPerformList(user.getAccountId(), leagueId);
            if (!CollectionUtils.isEmpty(recentPerformList)) {
                result.put("recentPerformList", recentPerformList);
            }
            return AjaxResult.success(200, result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("系统错误");
        }

    }


}

