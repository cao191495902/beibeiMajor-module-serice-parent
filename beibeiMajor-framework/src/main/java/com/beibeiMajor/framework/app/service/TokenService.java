package com.beibeiMajor.framework.app.service;


import com.beibeiMajor.system.domain.WebUser;

/**
 * 令牌相关接口
 */
public interface TokenService {


    /**
     * 将用户缓存
     *
     * @param token
     * @param webUser
     */
    boolean addTokenToCache(String token, WebUser webUser);

    /**
     * 重置缓存用户
     *
     * @param token
     * @param webUser
     */
    boolean resetTokenUser(String token, WebUser webUser);

    /**
     * 清除用户缓存
     *
     * @param token
     */
    void cleanTokenUserByToken(String token);

    /**
     * 清除渠道号登陆的所有用户缓存
     *
     * @param accountId 用户
     */
    void cleanTokenUserByChannelId(Long accountId);


    /**
     * 根据token获取用户
     *
     * @param token
     * @return
     */
    WebUser getChannelByToken(String token);

}
