package com.beibeiMajor.system.mapper;

import com.beibeiMajor.system.domain.WebUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface WebUserMapper 
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
     * 删除用户信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    public int deleteWebUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebUserByIds(String[] userIds);

    int checkLoginNameUnique(@Param("loginName")String loginName);

    WebUser selectUserByLoginName(String username);

    void insertByMatchDetails();

    List<WebUser> getAllWebUser();

    void updateToDefaultDoubleTimes();

    /**
     * 查询用户信息
     *
     * @param accountId 用户信息ID
     * @return 用户信息
     */
    WebUser selectWebUserByAccountId(Long accountId);
}
