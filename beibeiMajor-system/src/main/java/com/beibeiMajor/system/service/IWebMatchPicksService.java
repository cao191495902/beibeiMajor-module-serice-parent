package com.beibeiMajor.system.service;

import java.util.List;
import com.beibeiMajor.system.domain.WebMatchPicks;

/**
 * 比赛bpService接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface IWebMatchPicksService 
{
    /**
     * 查询比赛bp
     * 
     * @param matchId 比赛bpID
     * @return 比赛bp
     */
    public WebMatchPicks selectWebMatchPicksById(Long matchId);

    /**
     * 查询比赛bp列表
     * 
     * @param webMatchPicks 比赛bp
     * @return 比赛bp集合
     */
    public List<WebMatchPicks> selectWebMatchPicksList(WebMatchPicks webMatchPicks);

    /**
     * 新增比赛bp
     * 
     * @param webMatchPicks 比赛bp
     * @return 结果
     */
    public int insertWebMatchPicks(WebMatchPicks webMatchPicks);

    /**
     * 修改比赛bp
     * 
     * @param webMatchPicks 比赛bp
     * @return 结果
     */
    public int updateWebMatchPicks(WebMatchPicks webMatchPicks);

    /**
     * 批量删除比赛bp
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebMatchPicksByIds(String ids);

    /**
     * 删除比赛bp信息
     * 
     * @param matchId 比赛bpID
     * @return 结果
     */
    public int deleteWebMatchPicksById(Long matchId);
}
