package com.beibeiMajor.system.service;

import com.beibeiMajor.system.domain.WebLeague;
import com.beibeiMajor.system.domain.WebMatchDetail;

import java.util.List;

/**
 * 比赛详情Service接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface IWebMatchDetailService 
{
    /**
     * 查询比赛详情
     * 
     * @param matchId 比赛详情ID
     * @return 比赛详情
     */
    public WebMatchDetail selectWebMatchDetailById(Long matchId);

    /**
     * 查询比赛详情列表
     * 
     * @param webMatchDetail 比赛详情
     * @return 比赛详情集合
     */
    public List<WebMatchDetail> selectWebMatchDetailList(WebMatchDetail webMatchDetail);

    /**
     * 新增比赛详情
     * 
     * @param webMatchDetail 比赛详情
     * @return 结果
     */
    public int insertWebMatchDetail(WebMatchDetail webMatchDetail);

    /**
     * 修改比赛详情
     * 
     * @param webMatchDetail 比赛详情
     * @return 结果
     */
    public int updateWebMatchDetail(WebMatchDetail webMatchDetail);

    /**
     * 批量删除比赛详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebMatchDetailByIds(String ids);

    /**
     * 删除比赛详情信息
     * 
     * @param matchId 比赛详情ID
     * @return 结果
     */
    public int deleteWebMatchDetailById(Long matchId);

    WebLeague getDefaultLeagueInfo();
}
