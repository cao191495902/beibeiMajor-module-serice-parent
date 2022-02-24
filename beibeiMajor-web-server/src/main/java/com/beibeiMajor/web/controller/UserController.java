package com.beibeiMajor.web.controller;

import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.PageDomain;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.core.page.TableSupport;
import com.beibeiMajor.common.utils.DateUtils;
import com.beibeiMajor.common.utils.ServletUtils;
import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.framework.manager.JedisManager;
import com.beibeiMajor.framework.util.RSAUtil;
import com.beibeiMajor.framework.util.ShiroUtils;
import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;
import com.beibeiMajor.system.domain.WebLeague;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;
import com.beibeiMajor.web.service.OperationInfoToDBService;
import com.beibeiMajor.web.service.ReportInfoService;
import com.beibeiMajor.web.service.dto.MyMatchDetailBean;
import com.beibeiMajor.web.service.dto.TopBean;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 主页和用户登录相关处理
 * @author cw
 */
@RestController
public class UserController extends BaseController{


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

    /**
     * 登录接口
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        //这个验证器和后台通用，用host来区分是哪个模块的用户，目前暂时使用后台的用户来验证,host-web前端，host-admin为后台

        if (StringUtils.isNotEmpty(password) && password.length() > 32) {
            password = RSAUtil.decryptRequestParam(password);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe,"web");
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }
    @GetMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return new ModelAndView("login");
    }

    //个人主页
    @GetMapping({"/index", "/"})
    public Object index(ModelMap mmap) {
        // 取身份信息
        WebUser user = ShiroUtils.getWebUser();
        //没有身份信息，跳转登录
        if (user == null) {
            return new ModelAndView("login");
        }
        List<WebLeague> leagueList = webUserService.getLeagueList();
         Integer leagueId = user.getLeagueId();
        if (leagueId == null) {
            leagueId = leagueList.get(0).getId();
        }
        user = webUserService.selectWebUserByAccountId(user.getAccountId());
        user.setLeagueId(leagueId);

        WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
        webUserDotaReport.setUserId(user.getAccountId());
        webUserDotaReport.setLeagueId(user.getLeagueId());
        List<WebUserDotaReportPo> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport, 1, 10);
        if(CollectionUtils.isNotEmpty(list)){
            mmap.addAttribute("info",list.get(0));
        }else {
            mmap.addAttribute("info",new WebUserDotaReportPo());
        }
        mmap.addAttribute("user",user);

        for (WebLeague web : leagueList) {
            if (web.getId().equals(leagueId)) {
                mmap.addAttribute("league",web);
                break;
            }
        }




        Date startTime = DateUtils.getStartTimeOfDay(new Date());
        Date endTime = DateUtils.getStartTimeOfDay(DateUtils.addDays(startTime,1));
        //查询当天是否报过名
        WebDoubleIntegralRecord record = iWebDoubleIntegralRecordService.selectByTodayAndAccountId(user.getAccountId(), startTime.getTime() / 1000, endTime.getTime()/1000);
        mmap.addAttribute("record", record);

        //查询联赛信息
        mmap.addAttribute("leagueList", leagueList);
        ShiroUtils.setSysUser(user);
        return new ModelAndView("index");
    }

    //我的战绩列表
    @GetMapping("/playRecord")
    public Object myPlayRecord(ModelMap mmap,String accountId) {

        mmap.addAttribute("accountId", accountId);
        return new ModelAndView("playRecord");
    }

    /**
     * 获取用户积分列表
     *
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        // 取身份信息
        WebUser user = ShiroUtils.getWebUser();
        WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
        webUserDotaReport.setLeagueId(user.getLeagueId());
        startPage();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<WebUserDotaReportPo> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport, pageDomain.getPageNum(), pageDomain.getPageSize());
        PageHelper.clearPage();
        return getDataTable(list);
    }

    /**
     * 获取top信息表
     *
     * @return
     */
    @PostMapping("/topList")
    @ResponseBody
    public TableDataInfo topList() {
        WebUser user = ShiroUtils.getWebUser();
        Integer leagueId = user.getLeagueId();

        List<TopBean> result = reportInfoService.statisticsTopInfoList(leagueId);

        return getDataTable(result);
    }

    /**
     * 获取loss信息表
     *
     * @return
     */
    @PostMapping("/lossList")
    @ResponseBody
    public TableDataInfo lossList() {
        WebUser user = ShiroUtils.getWebUser();
        Integer leagueId = user.getLeagueId();
        List<TopBean> result = reportInfoService.statisticsLossInfoList(leagueId);

        return getDataTable(result);
    }

    /**
     * 获取我的战绩
     *
     * @return
     */
    @PostMapping("/myRecordList")
    @ResponseBody
    public TableDataInfo myRecordList(String accountId) {
        WebUser user = ShiroUtils.getWebUser();
        Integer leagueId = user.getLeagueId();
        startPage();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<MyMatchDetailBean> list = reportInfoService.getMyRecordList(accountId,leagueId,pageDomain.getPageNum(),pageDomain.getPageSize());
        PageHelper.clearPage();
        return getDataTable(list);
    }


    /**
     * 获取我最近五场的战绩
     *
     * @return
     */
    @PostMapping("/myRecordListRecent")
    @ResponseBody
    public TableDataInfo myRecordListRecent(String accountId) {
        WebUser user = ShiroUtils.getWebUser();
        Integer leagueId = user.getLeagueId();
        startPage();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<MyMatchDetailBean> list = reportInfoService.getMyRecordList(accountId,leagueId, pageDomain.getPageNum(), pageDomain.getPageSize());
        List<MyMatchDetailBean> newList = list.subList(0, list.size() > 5 ? 5 : list.size());
        PageHelper.clearPage();
        return getDataTable(newList);
    }

    /**
     * 报名
     *
     * @return
     */
    @RequestMapping("/updateDoubleTimes")
    @ResponseBody
//    @RepeatSubmit
    public Object updateDoubleTimes(WebUserDotaReport webUserDotaReport) {


        // 取身份信息
        WebUser user = ShiroUtils.getWebUser();
        //没有身份信息，跳转登录
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }

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
        return AjaxResult.success();
    }


    /**
     * 更换联赛信息
     *
     * @return
     */
    @RequestMapping("/changeLeagueId")
    @ResponseBody
    public Object changeLeagueId(@RequestParam(value = "leagueId", required = true) Integer leagueId) {

        // 取身份信息
        WebUser user = ShiroUtils.getWebUser();
        //没有身份信息，跳转登录
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        user.setLeagueId(leagueId);

        ShiroUtils.setSysUser(user);
        return AjaxResult.success();
    }


}

