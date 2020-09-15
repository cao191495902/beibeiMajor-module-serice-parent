package com.beibeiMajor.system.service;

import java.util.List;
import com.beibeiMajor.system.domain.WebUserDotaReport;

/**
 * 用户积分Service接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface IWebUserDotaReportService 
{
    /**
     * 查询用户积分
     * 
     * @param userId 用户积分ID
     * @return 用户积分
     */
    public WebUserDotaReport selectWebUserDotaReportById(Long userId);

    /**
     * 查询用户积分列表
     * 
     * @param webUserDotaReport 用户积分
     * @return 用户积分集合
     */
    public List<WebUserDotaReport> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport);

    /**
     * 新增用户积分
     * 
     * @param webUserDotaReport 用户积分
     * @return 结果
     */
    public int insertWebUserDotaReport(WebUserDotaReport webUserDotaReport);

    /**
     * 修改用户积分
     * 
     * @param webUserDotaReport 用户积分
     * @return 结果
     */
    public int updateWebUserDotaReport(WebUserDotaReport webUserDotaReport);

    /**
     * 批量删除用户积分
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebUserDotaReportByIds(String ids);

    /**
     * 删除用户积分信息
     * 
     * @param userId 用户积分ID
     * @return 结果
     */
    public int deleteWebUserDotaReportById(Long userId);
}
