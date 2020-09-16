package com.beibeiMajor.web.controller;

import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.utils.ServletUtils;
import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.framework.util.ShiroUtils;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.web.service.ReportInfoService;
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
import java.util.List;

/**
 * 主页和用户登录相关处理
 * @author cw
 */
@RestController
public class UserController extends BaseController{


    @Autowired
    ReportInfoService reportInfoService;

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
        WebUserDotaReport webUserDotaReport = new WebUserDotaReport();
        webUserDotaReport.setUserId(user.getAccountId());
        List<WebUserDotaReport> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport);
        if(CollectionUtils.isNotEmpty(list)){
            mmap.addAttribute("info",list.get(0));
        }
        mmap.addAttribute("user",user);
        return new ModelAndView("index");
    }

    /**
     * 获取用户积分列表
     * @param webUserDotaReport
     * @return
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebUserDotaReport webUserDotaReport)
    {
        startPage();
        List<WebUserDotaReport> list = reportInfoService.selectWebUserDotaReportList(webUserDotaReport);
        return getDataTable(list);
    }
}

