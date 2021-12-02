package com.beibeiMajor.framework.shiro.service;

import com.beibeiMajor.common.constant.Constants;
import com.beibeiMajor.common.constant.ShiroConstants;
import com.beibeiMajor.common.exception.user.UserPasswordNotMatchException;
import com.beibeiMajor.common.exception.user.UserPasswordRetryLimitExceedException;
import com.beibeiMajor.common.utils.MessageUtils;
import com.beibeiMajor.framework.manager.AsyncManager;
import com.beibeiMajor.framework.manager.factory.AsyncFactory;
import com.beibeiMajor.system.domain.WebUser;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 * 
 * @author ruoyi
 */
@Component
public class WebPasswordService
{
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }

    public void validate(WebUser user, String password)
    {
        String loginName = user.getAccountId()+"";

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(WebUser user, String newPassword)
    {
        return user.getPassword().equals(new Md5Hash(newPassword).toHex());
    }

    public void clearLoginRecordCache(String username)
    {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password,String salt)
    {
        return new Md5Hash(username + password+salt ).toHex();
    }
    public void unlock(String loginName)
    {
        loginRecordCache.remove(loginName);
    }
}
