package com.beibeiMajor.system.mapper;

import com.beibeiMajor.system.domain.WebAbilityUpgrades;

import java.util.List;

/**
 * 比赛玩家加点记录Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface WebAbilityUpgradesMapper 
{
    /**
     * 查询比赛玩家加点记录
     * 
     * @param id 比赛玩家加点记录ID
     * @return 比赛玩家加点记录
     */
    public WebAbilityUpgrades selectWebAbilityUpgradesById(Long id);

    /**
     * 查询比赛玩家加点记录列表
     * 
     * @param webAbilityUpgrades 比赛玩家加点记录
     * @return 比赛玩家加点记录集合
     */
    public List<WebAbilityUpgrades> selectWebAbilityUpgradesList(WebAbilityUpgrades webAbilityUpgrades);

    /**
     * 新增比赛玩家加点记录
     * 
     * @param webAbilityUpgrades 比赛玩家加点记录
     * @return 结果
     */
    public int insertWebAbilityUpgrades(WebAbilityUpgrades webAbilityUpgrades);

    /**
     * 修改比赛玩家加点记录
     * 
     * @param webAbilityUpgrades 比赛玩家加点记录
     * @return 结果
     */
    public int updateWebAbilityUpgrades(WebAbilityUpgrades webAbilityUpgrades);

    /**
     * 删除比赛玩家加点记录
     * 
     * @param id 比赛玩家加点记录ID
     * @return 结果
     */
    public int deleteWebAbilityUpgradesById(Long id);

    /**
     * 批量删除比赛玩家加点记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebAbilityUpgradesByIds(String[] ids);
}
