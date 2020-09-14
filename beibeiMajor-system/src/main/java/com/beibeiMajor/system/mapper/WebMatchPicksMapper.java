package com.beibeiMajor.system.mapper;

import java.util.List;
import com.beibeiMajor.system.domain.WebMatchPicks;

/**
 * 比赛bpMapper接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface WebMatchPicksMapper 
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
     * 删除比赛bp
     * 
     * @param matchId 比赛bpID
     * @return 结果
     */
    public int deleteWebMatchPicksById(Long matchId);

    /**
     * 批量删除比赛bp
     * 
     * @param matchIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebMatchPicksByIds(String[] matchIds);
}
