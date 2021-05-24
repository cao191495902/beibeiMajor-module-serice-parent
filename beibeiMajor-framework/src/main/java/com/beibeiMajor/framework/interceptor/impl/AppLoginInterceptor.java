package com.beibeiMajor.framework.interceptor.impl;

import com.alibaba.fastjson.JSON;
import com.beibeiMajor.common.annotation.ApiLogin;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.utils.ServletUtils;
import com.beibeiMajor.framework.app.service.TokenService;
import com.beibeiMajor.framework.util.RequestUtils;
import com.beibeiMajor.system.domain.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移动端登录拦截器
 */
@Component
public  class AppLoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        try {
            String contextPath = request.getContextPath();
            uri = uri.replace(contextPath, "");

            HandlerMethod handlerMethod;
            if (handler instanceof HandlerMethod) {
                handlerMethod = (HandlerMethod) handler;
            } else {
                return true;
            }
            //需要登录的权限检查
            ApiLogin needLogin = handlerMethod.getMethod().getAnnotation(ApiLogin.class);
            if (needLogin != null) {
                String accessToken = RequestUtils.getString(request, "token");
                WebUser webUser = tokenService.getChannelByToken(accessToken);
                if (webUser == null) {

                    AjaxResult ajaxResult = AjaxResult.error(4002, "登录已失效，请重新登录");
                    ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            AjaxResult ajaxResult = AjaxResult.error("系统错误");
            ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
            return false;
        }
    }


}
