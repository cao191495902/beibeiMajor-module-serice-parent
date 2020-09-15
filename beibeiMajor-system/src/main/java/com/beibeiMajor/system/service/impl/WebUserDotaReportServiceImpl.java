package com.beibeiMajor.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beibeiMajor.system.mapper.WebUserDotaReportMapper;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.system.service.IWebUserDotaReportService;
import com.beibeiMajor.common.core.text.Convert;

/**
 * 用户积分Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebUserDotaReportServiceImpl implements IWebUserDotaReportService 
{
    @Autowired
    private WebUserDotaReportMapper webUserDotaReportMapper;

    /**
     * 查询用户积分
     * 
     * @param userId 用户积分ID
     * @return 用户积分
     */
    @Override
    public WebUserDotaReport selectWebUserDotaReportById(Long userId)
    {
        return webUserDotaReportMapper.selectWebUserDotaReportById(userId);
    }

    /**
     * 查询用户积分列表
     * 
     * @param webUserDotaReport 用户积分
     * @return 用户积分
     */
    @Override
    public List<WebUserDotaReport> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport)
    {
        return webUserDotaReportMapper.selectWebUserDotaReportList(webUserDotaReport);
    }

    /**
     * 新增用户积分
     * 
     * @param webUserDotaReport 用户积分
     * @return 结果
     */
    @Override
    public int insertWebUserDotaReport(WebUserDotaReport webUserDotaReport)
    {
        return webUserDotaReportMapper.insertWebUserDotaReport(webUserDotaReport);
    }

    /**
     * 修改用户积分
     * 
     * @param webUserDotaReport 用户积分
     * @return 结果
     */
    @Override
    public int updateWebUserDotaReport(WebUserDotaReport webUserDotaReport)
    {
        return webUserDotaReportMapper.updateWebUserDotaReport(webUserDotaReport);
    }

    /**
     * 删除用户积分对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebUserDotaReportByIds(String ids)
    {
        return webUserDotaReportMapper.deleteWebUserDotaReportByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户积分信息
     * 
     * @param userId 用户积分ID
     * @return 结果
     */
    @Override
    public int deleteWebUserDotaReportById(Long userId)
    {
        return webUserDotaReportMapper.deleteWebUserDotaReportById(userId);
    }
}
