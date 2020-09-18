package com.beibeiMajor.system.service;

import com.beibeiMajor.system.domain.WebUser;

import java.util.List;

/**
 * 用户信息Service接口
 * 
 * @author ruoyi
 * @date 2020-09-15
 */
public interface IWebUserService 
{
    /**
     * 查询用户信息
     * 
     * @param userId 用户信息ID
     * @return 用户信息
     */
    public WebUser selectWebUserById(Long userId);

    /**
     * 查询用户信息列表
     * 
     * @param webUser 用户信息
     * @return 用户信息集合
     */
    public List<WebUser> selectWebUserList(WebUser webUser);

    /**
     * 新增用户信息
     * 
     * @param webUser 用户信息
     * @return 结果
     */
    public int insertWebUser(WebUser webUser);

    /**
     * 修改用户信息
     * 
     * @param webUser 用户信息
     * @return 结果
     */
    public int updateWebUser(WebUser webUser);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebUserByIds(String ids);

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    public int deleteWebUserById(Long userId);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);

    /**
     * 前端注册
     * @param user
     * @return
     */
    boolean registerUser(WebUser user);

    /**
     * 通过登录名称获取用户
     * @param username
     * @return
     */
    WebUser selectUserByLoginName(String username);

    /**
     * 通过steam的api更新webinfo
     * @param webUser
     * @return
     */
    WebUser getUpdateWebUserInfo(WebUser webUser);

    /**
     * 更新所有用户信息
     */
    void selectInsertWebUser();
}
