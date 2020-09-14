package com.beibeiMajor.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beibeiMajor.system.mapper.WebMatchPicksMapper;
import com.beibeiMajor.system.domain.WebMatchPicks;
import com.beibeiMajor.system.service.IWebMatchPicksService;
import com.beibeiMajor.common.core.text.Convert;

/**
 * 比赛bpService业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebMatchPicksServiceImpl implements IWebMatchPicksService 
{
    @Autowired
    private WebMatchPicksMapper webMatchPicksMapper;

    /**
     * 查询比赛bp
     * 
     * @param matchId 比赛bpID
     * @return 比赛bp
     */
    @Override
    public WebMatchPicks selectWebMatchPicksById(Long matchId)
    {
        return webMatchPicksMapper.selectWebMatchPicksById(matchId);
    }

    /**
     * 查询比赛bp列表
     * 
     * @param webMatchPicks 比赛bp
     * @return 比赛bp
     */
    @Override
    public List<WebMatchPicks> selectWebMatchPicksList(WebMatchPicks webMatchPicks)
    {
        return webMatchPicksMapper.selectWebMatchPicksList(webMatchPicks);
    }

    /**
     * 新增比赛bp
     * 
     * @param webMatchPicks 比赛bp
     * @return 结果
     */
    @Override
    public int insertWebMatchPicks(WebMatchPicks webMatchPicks)
    {
        return webMatchPicksMapper.insertWebMatchPicks(webMatchPicks);
    }

    /**
     * 修改比赛bp
     * 
     * @param webMatchPicks 比赛bp
     * @return 结果
     */
    @Override
    public int updateWebMatchPicks(WebMatchPicks webMatchPicks)
    {
        return webMatchPicksMapper.updateWebMatchPicks(webMatchPicks);
    }

    /**
     * 删除比赛bp对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebMatchPicksByIds(String ids)
    {
        return webMatchPicksMapper.deleteWebMatchPicksByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除比赛bp信息
     * 
     * @param matchId 比赛bpID
     * @return 结果
     */
    @Override
    public int deleteWebMatchPicksById(Long matchId)
    {
        return webMatchPicksMapper.deleteWebMatchPicksById(matchId);
    }
}
