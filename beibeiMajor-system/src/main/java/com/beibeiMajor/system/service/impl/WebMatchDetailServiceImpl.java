package com.beibeiMajor.system.service.impl;

import com.beibeiMajor.common.core.text.Convert;
import com.beibeiMajor.system.domain.WebLeague;
import com.beibeiMajor.system.domain.WebMatchDetail;
import com.beibeiMajor.system.mapper.WebMatchDetailMapper;
import com.beibeiMajor.system.service.IWebMatchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 比赛详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebMatchDetailServiceImpl implements IWebMatchDetailService 
{
    @Autowired
    private WebMatchDetailMapper webMatchDetailMapper;


    /**
     * 查询比赛详情
     * 
     * @param matchId 比赛详情ID
     * @return 比赛详情
     */
    @Override
    public WebMatchDetail selectWebMatchDetailById(Long matchId)
    {
        return webMatchDetailMapper.selectWebMatchDetailById(matchId);
    }

    /**
     * 查询比赛详情列表
     * 
     * @param webMatchDetail 比赛详情
     * @return 比赛详情
     */
    @Override
    public List<WebMatchDetail> selectWebMatchDetailList(WebMatchDetail webMatchDetail)
    {
        return webMatchDetailMapper.selectWebMatchDetailList(webMatchDetail);
    }

    /**
     * 新增比赛详情
     * 
     * @param webMatchDetail 比赛详情
     * @return 结果
     */
    @Override
    public int insertWebMatchDetail(WebMatchDetail webMatchDetail)
    {
        return webMatchDetailMapper.insertWebMatchDetail(webMatchDetail);
    }

    /**
     * 修改比赛详情
     * 
     * @param webMatchDetail 比赛详情
     * @return 结果
     */
    @Override
    public int updateWebMatchDetail(WebMatchDetail webMatchDetail)
    {
        return webMatchDetailMapper.updateWebMatchDetail(webMatchDetail);
    }

    /**
     * 删除比赛详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebMatchDetailByIds(String ids)
    {
        return webMatchDetailMapper.deleteWebMatchDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除比赛详情信息
     * 
     * @param matchId 比赛详情ID
     * @return 结果
     */
    @Override
    public int deleteWebMatchDetailById(Long matchId)
    {
        return webMatchDetailMapper.deleteWebMatchDetailById(matchId);
    }

    @Override
    @Cacheable(value = "getDefaultLeagueInfo", key = "#root.methodName")
    public WebLeague getDefaultLeagueInfo() {
        return webMatchDetailMapper.getDefaultLeagueInfo();
    }
}
