package com.beibeiMajor.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * (WebMatchPlayerInfo)实体类
 *
 * @author makejava
 * @since 2020-09-14 10:33:43
 */
@Data
public class WebMatchPlayerPo implements Serializable {
    private static final long serialVersionUID = 199858559263554011L;

    private Long id;
    /**
     * 用户ID
     */
    private Long accountId;
    /**
     * 比赛ID
     */
    private Long matchId;

    private Integer playerSlot;
    /**
     * 英雄ID
     */
    private Integer heroId;
    /**
     * 左上方物品ID
     */
    private Integer item0;
    /**
     * 中间上方物品ID
     */
    private Integer item1;
    /**
     * 右上方物品ID
     */
    private Integer item2;
    /**
     * 左下方物品ID
     */
    private Integer item3;
    /**
     * 中间下方物品ID
     */
    private Integer item4;
    /**
     * 右下方物品ID
     */
    private Integer item5;
    /**
     * 备用库存物品1的ID
     */
    private Integer backpack0;
    /**
     * 备用库存物品2的ID
     */
    private Integer backpack1;
    /**
     * 备用库存物品3的ID
     */
    private Integer backpack2;
    /**
     * 该玩家的击杀数
     */
    private Integer kills;
    /**
     * 该玩家的死亡数
     */
    private Integer deaths;
    /**
     * 该玩家的助攻数
     */
    private Integer assists;
    /**
     * 该玩家的正补数
     */
    private Integer lastHits;
    /**
     * 该玩家的反补数
     */
    private Integer denies;
    /**
     * 该玩家的GPM
     */
    private Integer goldPerMin;
    /**
     * 该玩家的XPM
     */
    private Integer xpPerMin;
    /**
     * 该玩家的等级
     */
    private Integer level;
    /**
     * 该玩家的英雄伤害
     */
    private Integer heroDamage;
    /**
     * 该玩家的防御塔伤害
     */
    private Integer towerDamage;
    /**
     * 该玩家的治疗量
     */
    private Integer heroHealing;
    /**
     * 该玩家的总金钱
     */
    private Integer gold;
    /**
     * 该玩家花费的金钱
     */
    private Integer goldSpent;
    /**
     * 该玩家的英雄伤害占比
     */
    private Integer scaledHeroDamage;
    /**
     * 该玩家的防御塔伤害占比
     */
    private Integer scaledTowerDamage;
    /**
     * 该玩家的英雄治疗占比
     */
    private Integer scaledHeroHealing;

}