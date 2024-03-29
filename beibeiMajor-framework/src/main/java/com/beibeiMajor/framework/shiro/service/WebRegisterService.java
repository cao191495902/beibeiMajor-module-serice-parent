package com.beibeiMajor.framework.shiro.service;

import com.beibeiMajor.common.constant.Constants;
import com.beibeiMajor.common.constant.ShiroConstants;
import com.beibeiMajor.common.constant.UserConstants;
import com.beibeiMajor.common.utils.MessageUtils;
import com.beibeiMajor.common.utils.ServletUtils;
import com.beibeiMajor.common.utils.security.Md5Utils;
import com.beibeiMajor.framework.manager.AsyncManager;
import com.beibeiMajor.framework.manager.factory.AsyncFactory;
import com.beibeiMajor.framework.util.ShiroUtils;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.service.IWebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 注册校验方法
 * 
 * @author ruoyi
 */
@Component
public class WebRegisterService
{
    @Autowired
    private IWebUserService userService;

    @Autowired
    private WebPasswordService passwordService;

    /**
     * 注册
     */
    public String register(WebUser user)
    {
        String msg = "", username = user.getLoginName(), password = user.getPassword();

        String steamId = user.getSteamId();

        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            msg = "验证码错误";
        }
        else if (StringUtils.isEmpty(username))
        {
            msg = "用户名不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            msg = "密码长度必须在5到20个字符之间";
        }
        else if (steamId == null){
            msg = "steamId不能为空";
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            msg = "账户长度必须在2到20个字符之间";
        }
        else if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(username)))
        {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        }
        else
        {
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(Md5Utils.hash(user.getPassword()));
            boolean regFlag = userService.registerUser(user);
            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }
}
