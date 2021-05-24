package com.beibeiMajor.framework.app.service.impl;

import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.framework.app.service.TokenService;
import com.beibeiMajor.framework.manager.JedisManager;
import com.beibeiMajor.system.domain.WebUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("tokenService")
@Slf4j
public class TokenServiceImpl implements TokenService {

    /**
     * mid映射token
     */
    private final static String TOKEN_KEY = "user_token_%s"; // redis中token key前缀
    private final static String ALL_TOKEN_HASH = "user_all_tk_%s"; // redis中所有token hash key
    private final static long TOKEN_AGE = 7 * 24 * 60 * 60; // 登录token有效期（秒）

    @Autowired
    private JedisManager jedisManager;

    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public boolean addTokenToCache(String token, WebUser webUser) {
        if (StringUtils.isBlank(token) || webUser == null) {
            return false;
        }
        try {
            // 允许同时，产生不同的有效token需要关联到channelId，以便将指定用户踢下线
            String allTokenHash = String.format(ALL_TOKEN_HASH, webUser.getAccountId());
            Set<String> tokenSet = redisTemplate.opsForHash().keys(allTokenHash);
            if (tokenSet != null && tokenSet.size() > 0) {
                // 先检查是否有过期的，有则删除hash中的key-value
                for (String tKey : tokenSet) {
                    if (!jedisManager.exists(tKey)) {
                        redisTemplate.opsForHash().delete(allTokenHash, tKey);
                    }
                }
            }
            // 加入缓存
            String tokenKey = String.format(TOKEN_KEY, token);
            jedisManager.set(tokenKey, webUser, TOKEN_AGE, TimeUnit.SECONDS);
            jedisManager.hmSet(allTokenHash, tokenKey, "1");
            redisTemplate.expire(allTokenHash, TOKEN_AGE, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean resetTokenUser(String token, WebUser webUser) {
        if (StringUtils.isBlank(token) || webUser == null) {
            return false;
        }
        try {
            String tokenKey = String.format(TOKEN_KEY, token);
            jedisManager.set(tokenKey, webUser, TOKEN_AGE,TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void cleanTokenUserByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        String tokenKey = String.format(TOKEN_KEY, token);
        redisTemplate.delete(tokenKey);
    }

    @Override
    public void cleanTokenUserByChannelId(Long channelId) {
        if (channelId == null) {
            return;
        }
        String allTokenHash = String.format(ALL_TOKEN_HASH, channelId);
        Set<String> tokenSet = redisTemplate.opsForHash().keys(allTokenHash);
        if (tokenSet != null && tokenSet.size() > 0) {
            for (String tKey : tokenSet) {
                redisTemplate.delete(tKey);
            }
            redisTemplate.delete(allTokenHash);
        }
    }

    @Override
    public WebUser getChannelByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String tokenKey = String.format(TOKEN_KEY, token);
        return (WebUser) jedisManager.get(tokenKey);
    }



}
