package com.beibeiMajor.system.service.impl;

import com.beibeiMajor.common.constant.UserConstants;
import com.beibeiMajor.common.core.text.Convert;
import com.beibeiMajor.common.utils.DateUtils;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.mapper.WebUserMapper;
import com.beibeiMajor.system.service.IWebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebUserServiceImpl implements IWebUserService 
{
    @Autowired
    private WebUserMapper webUserMapper;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息ID
     * @return 用户信息
     */
    @Override
    public WebUser selectWebUserById(Long userId)
    {
        return webUserMapper.selectWebUserById(userId);
    }

    /**
     * 查询用户信息列表
     * 
     * @param webUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<WebUser> selectWebUserList(WebUser webUser)
    {
        return webUserMapper.selectWebUserList(webUser);
    }

    /**
     * 新增用户信息
     * 
     * @param webUser 用户信息
     * @return 结果
     */
    @Override
    public int insertWebUser(WebUser webUser)
    {
        webUser.setCreateTime(DateUtils.getNowDate());
        return webUserMapper.insertWebUser(webUser);
    }

    /**
     * 修改用户信息
     * 
     * @param webUser 用户信息
     * @return 结果
     */
    @Override
    public int updateWebUser(WebUser webUser)
    {
        webUser.setUpdateTime(DateUtils.getNowDate());
        return webUserMapper.updateWebUser(webUser);
    }

    /**
     * 删除用户信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebUserByIds(String ids)
    {
        return webUserMapper.deleteWebUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteWebUserById(Long userId)
    {
        return webUserMapper.deleteWebUserById(userId);
    }

    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = webUserMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public boolean registerUser(WebUser user) {
        return webUserMapper.insertWebUser(user) > 0;
    }

    @Override
    public WebUser selectUserByLoginName(String username) {
        return webUserMapper.selectUserByLoginName(username);
    }
}
