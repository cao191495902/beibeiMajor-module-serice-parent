package com.beibeiMajor.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beibeiMajor.system.mapper.WebAbilityUpgradesMapper;
import com.beibeiMajor.system.domain.WebAbilityUpgrades;
import com.beibeiMajor.system.service.IWebAbilityUpgradesService;
import com.beibeiMajor.common.core.text.Convert;

/**
 * 比赛玩家加点记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebAbilityUpgradesServiceImpl implements IWebAbilityUpgradesService 
{
    @Autowired
    private WebAbilityUpgradesMapper webAbilityUpgradesMapper;

    /**
     * 查询比赛玩家加点记录
     * 
     * @param id 比赛玩家加点记录ID
     * @return 比赛玩家加点记录
     */
    @Override
    public WebAbilityUpgrades selectWebAbilityUpgradesById(Long id)
    {
        return webAbilityUpgradesMapper.selectWebAbilityUpgradesById(id);
    }

    /**
     * 查询比赛玩家加点记录列表
     * 
     * @param webAbilityUpgrades 比赛玩家加点记录
     * @return 比赛玩家加点记录
     */
    @Override
    public List<WebAbilityUpgrades> selectWebAbilityUpgradesList(WebAbilityUpgrades webAbilityUpgrades)
    {
        return webAbilityUpgradesMapper.selectWebAbilityUpgradesList(webAbilityUpgrades);
    }

    /**
     * 新增比赛玩家加点记录
     * 
     * @param webAbilityUpgrades 比赛玩家加点记录
     * @return 结果
     */
    @Override
    public int insertWebAbilityUpgrades(WebAbilityUpgrades webAbilityUpgrades)
    {
        return webAbilityUpgradesMapper.insertWebAbilityUpgrades(webAbilityUpgrades);
    }

    /**
     * 修改比赛玩家加点记录
     * 
     * @param webAbilityUpgrades 比赛玩家加点记录
     * @return 结果
     */
    @Override
    public int updateWebAbilityUpgrades(WebAbilityUpgrades webAbilityUpgrades)
    {
        return webAbilityUpgradesMapper.updateWebAbilityUpgrades(webAbilityUpgrades);
    }

    /**
     * 删除比赛玩家加点记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebAbilityUpgradesByIds(String ids)
    {
        return webAbilityUpgradesMapper.deleteWebAbilityUpgradesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除比赛玩家加点记录信息
     * 
     * @param id 比赛玩家加点记录ID
     * @return 结果
     */
    @Override
    public int deleteWebAbilityUpgradesById(Long id)
    {
        return webAbilityUpgradesMapper.deleteWebAbilityUpgradesById(id);
    }
}
