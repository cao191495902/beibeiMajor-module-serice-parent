package com.beibeiMajor.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beibeiMajor.system.mapper.WebMatchPlayerInfoMapper;
import com.beibeiMajor.system.domain.WebMatchPlayerInfo;
import com.beibeiMajor.system.service.IWebMatchPlayerInfoService;
import com.beibeiMajor.common.core.text.Convert;

/**
 * 比赛玩家详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebMatchPlayerInfoServiceImpl implements IWebMatchPlayerInfoService 
{
    @Autowired
    private WebMatchPlayerInfoMapper webMatchPlayerInfoMapper;

    /**
     * 查询比赛玩家详情
     * 
     * @param id 比赛玩家详情ID
     * @return 比赛玩家详情
     */
    @Override
    public WebMatchPlayerInfo selectWebMatchPlayerInfoById(Long id)
    {
        return webMatchPlayerInfoMapper.selectWebMatchPlayerInfoById(id);
    }

    /**
     * 查询比赛玩家详情列表
     * 
     * @param webMatchPlayerInfo 比赛玩家详情
     * @return 比赛玩家详情
     */
    @Override
    public List<WebMatchPlayerInfo> selectWebMatchPlayerInfoList(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        return webMatchPlayerInfoMapper.selectWebMatchPlayerInfoList(webMatchPlayerInfo);
    }

    /**
     * 新增比赛玩家详情
     * 
     * @param webMatchPlayerInfo 比赛玩家详情
     * @return 结果
     */
    @Override
    public int insertWebMatchPlayerInfo(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        return webMatchPlayerInfoMapper.insertWebMatchPlayerInfo(webMatchPlayerInfo);
    }

    /**
     * 修改比赛玩家详情
     * 
     * @param webMatchPlayerInfo 比赛玩家详情
     * @return 结果
     */
    @Override
    public int updateWebMatchPlayerInfo(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        return webMatchPlayerInfoMapper.updateWebMatchPlayerInfo(webMatchPlayerInfo);
    }

    /**
     * 删除比赛玩家详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebMatchPlayerInfoByIds(String ids)
    {
        return webMatchPlayerInfoMapper.deleteWebMatchPlayerInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除比赛玩家详情信息
     * 
     * @param id 比赛玩家详情ID
     * @return 结果
     */
    @Override
    public int deleteWebMatchPlayerInfoById(Long id)
    {
        return webMatchPlayerInfoMapper.deleteWebMatchPlayerInfoById(id);
    }
}
