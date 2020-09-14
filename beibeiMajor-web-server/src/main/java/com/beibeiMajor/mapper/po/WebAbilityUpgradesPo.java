package com.beibeiMajor.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * (WebAbilityUpgrades)实体类
 *
 * @author makejava
 * @since 2020-09-14 11:15:47
 */
@Data
public class WebAbilityUpgradesPo implements Serializable {
    private static final long serialVersionUID = 690355993419746610L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 比赛Id
     */
    private Long matchId;
    /**
     * 用户Id
     */
    private Long accountId;
    /**
     * 技能Id
     */
    private Long abilityId;
    /**
     * 加点时间
     */
    private Long time;
    /**
     * 玩家等级
     */
    private Integer level;

}