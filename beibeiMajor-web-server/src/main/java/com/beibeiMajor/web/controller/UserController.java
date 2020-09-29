package com.beibeiMajor.web.controller;

import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.utils.DateUtils;
import com.beibeiMajor.common.utils.ServletUtils;
import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.framework.util.ShiroUtils;
import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.service.ReportInfoService;
import com.beibeiMajor.web.service.dto.MyMatchDetailBean;
import com.beibeiMajor.web.service.dto.TopBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
    IWebDoubleIntegralRecordService iWebDoubleIntegralRecordService;

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
        user = webUserService.selectWebUserByAccountId(user.getAccountId());

        WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
        webUserDotaReport.setUserId(user.getAccountId());
        List<WebUserDotaReport> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport);
        if(CollectionUtils.isNotEmpty(list)){
            mmap.addAttribute("info",list.get(0));
        }
        mmap.addAttribute("user",user);

        Date startTime = DateUtils.getStartTimeOfDay(new Date());
        Date endTime = DateUtils.getStartTimeOfDay(DateUtils.addDays(startTime,1));
        //查询当天是否报过名
        WebDoubleIntegralRecord record = iWebDoubleIntegralRecordService.selectByTodayAndAccountId(user.getAccountId(), startTime.getTime() / 1000, endTime.getTime()/1000);
        mmap.addAttribute("record", record);
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
     * @param webUserDotaReport
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebUserDotaReport webUserDotaReport) {
        startPage();
        List<WebUserDotaReport> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport);
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
//        startPage();
        List<TopBean> result = reportInfoService.statisticsTopInfoList();

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
        startPage();
        List<MyMatchDetailBean> list = reportInfoService.getMyRecordList(accountId);

        return getDataTable(list);
    }

    /**
     * 报名
     *
     * @return
     */
    @PostMapping("/updateDoubleTimes")
    @ResponseBody
    public Object updateDoubleTimes(WebUserDotaReport webUserDotaReport) {
        // 取身份信息
        WebUser user = ShiroUtils.getWebUser();
        //没有身份信息，跳转登录
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        Date startTime = DateUtils.getStartTimeOfDay(new Date());
        Date endTime = DateUtils.getStartTimeOfDay(DateUtils.addDays(startTime,1));
        //查询当天是否报过名
        WebDoubleIntegralRecord record = iWebDoubleIntegralRecordService.selectByTodayAndAccountId(user.getAccountId(), startTime.getTime() / 1000, endTime.getTime()/1000);
        //查询报名次数是否够
        WebUser webUser = webUserService.selectWebUserByAccountId(user.getAccountId());
        if (webUser.getDoubleIntegralTimes() <= 0) {
            return AjaxResult.error("双倍次数不够");
        }
        //更新记录
        WebDoubleIntegralRecord newRecord = new WebDoubleIntegralRecord();
        newRecord.setAccountId(webUser.getAccountId());
        newRecord.setChangeTimes(-1L);
        newRecord.setMoney(new BigDecimal(0));
        newRecord.setCreatedBy(user.getNickName());
        newRecord.setCreatedTime(System.currentTimeMillis() / 1000);
        newRecord.setType(1);
        iWebDoubleIntegralRecordService.insertWebDoubleIntegralRecord(newRecord);
        //减去用户次数
        webUser.setDoubleIntegralTimes(webUser.getDoubleIntegralTimes() - 1);
        webUserService.updateWebUser(webUser);
        ShiroUtils.setSysUser(webUser);
        return AjaxResult.success();
    }
}

