package com.beibeiMajor.system.mapper;

import java.util.List;
import com.beibeiMajor.system.domain.WebMatchPlayerInfo;

/**
 * 比赛玩家详情Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface WebMatchPlayerInfoMapper 
{
    /**
     * 查询比赛玩家详情
     * 
     * @param id 比赛玩家详情ID
     * @return 比赛玩家详情
     */
    public WebMatchPlayerInfo selectWebMatchPlayerInfoById(Long id);

    /**
     * 查询比赛玩家详情列表
     * 
     * @param webMatchPlayerInfo 比赛玩家详情
     * @return 比赛玩家详情集合
     */
    public List<WebMatchPlayerInfo> selectWebMatchPlayerInfoList(WebMatchPlayerInfo webMatchPlayerInfo);

    /**
     * 新增比赛玩家详情
     * 
     * @param webMatchPlayerInfo 比赛玩家详情
     * @return 结果
     */
    public int insertWebMatchPlayerInfo(WebMatchPlayerInfo webMatchPlayerInfo);

    /**
     * 修改比赛玩家详情
     * 
     * @param webMatchPlayerInfo 比赛玩家详情
     * @return 结果
     */
    public int updateWebMatchPlayerInfo(WebMatchPlayerInfo webMatchPlayerInfo);

    /**
     * 删除比赛玩家详情
     * 
     * @param id 比赛玩家详情ID
     * @return 结果
     */
    public int deleteWebMatchPlayerInfoById(Long id);

    /**
     * 批量删除比赛玩家详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebMatchPlayerInfoByIds(String[] ids);
}
