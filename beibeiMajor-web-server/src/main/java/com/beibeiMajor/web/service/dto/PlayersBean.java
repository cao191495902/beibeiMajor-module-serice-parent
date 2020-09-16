package com.beibeiMajor.web.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class PlayersBean {

    /**
     * 玩家ID
     */
    @JSONField(name = "account_id")
    private Long accountId;
    /**
     * 自增主键
     */
    @JSONField(name = "player_slot")
    private int playerSlot;
    /**
     * 英雄ID
     */
    @JSONField(name = "hero_id")
    private int heroId;
    /**
     * 左上方库存商品的ID
     */
    @JSONField(name = "item_0")
    private int item0;
    /**
     * 顶部居中库存商品的ID
     */
    @JSONField(name = "item_1")
    private int item1;
    /**
     * 右上方库存商品的ID
     */
    @JSONField(name = "item_2")
    private int item2;
    /**
     * 左上方库存商品的ID
     */
    @JSONField(name = "item_3")
    private int item3;
    /**
     * 底部居中库存商品的ID
     */
    @JSONField(name = "item_4")
    private int item4;
    /**
     * 右下库存物品的ID
     */
    @JSONField(name = "item_5")
    private int item5;
    /**
     * 备用库存物品1的ID
     */
    @JSONField(name = "backpack_0")
    private int backpack0;
    /**
     * 备用库存物品2的ID
     */
    @JSONField(name = "backpack_1")
    private int backpack1;
    /**
     * 备用库存物品3的ID
     */
    @JSONField(name = "backpack_2")
    private int backpack2;

    @JSONField(name = "item_neutral")
    private int itemNeutral;
    /**
     * 该玩家的击杀数
     */
    @JSONField(name = "kills")
    private int kills;
    /**
     * 该玩家的死亡数
     */
    @JSONField(name = "deaths")
    private int deaths;
    /**
     * 该玩家的助攻数
     */
    @JSONField(name = "assists")
    private int assists;
    /**
     * 该玩家的比赛状态（0-无-完成比赛，不放弃。
     * 1-已断开连接-播放器DC，无放弃。
     * 2-DISCONNECTED_TOO_LONG-玩家DC> 5分钟，被放弃。
     * 3-放弃-玩家DC，点击离开，被放弃。
     * 4-AFK-玩家AFK，已放弃。
     * 5-NEVER_CONNECTED-玩家从未连接，也没有放弃。
     * 6-NEVER_CONNECTED_TOO_LONG-玩家花费太长时间进行连接，没有放弃。）
     */
    @JSONField(name = "leaver_status")
    private int leaverStatus;
    /**
     * 该玩家的正补数
     */
    @JSONField(name = "last_hits")
    private int lastHits;
    /**
     * 该玩家的反补数
     */
    @JSONField(name = "denies")
    private int denies;
    /**
     * 该玩家的GPM
     */
    @JSONField(name = "gold_per_min")
    private int goldPerMin;
    /**
     * 该玩家的XPM
     */
    @JSONField(name = "xp_per_min")
    private int xpPerMin;
    /**
     * 该玩家的等级
     */
    @JSONField(name = "level")
    private int level;
    /**
     * 该玩家的英雄伤害
     */
    @JSONField(name = "hero_damage")
    private int heroDamage;
    /**
     * 该玩家的防御塔伤害
     */
    @JSONField(name = "tower_damage")
    private int towerDamage;
    /**
     * 该玩家的治疗量
     */
    @JSONField(name = "hero_healing")
    private int heroHealing;
    /**
     * 该玩家的总金钱
     */
    @JSONField(name = "gold")
    private int gold;
    /**
     * 该玩家花费的金钱
     */
    @JSONField(name = "gold_spent")
    private int goldSpent;
    /**
     * 该玩家的英雄伤害占比
     */
    @JSONField(name = "scaled_hero_damage")
    private int scaledHeroDamage;
    /**
     * 该玩家的防御塔伤害占比
     */
    @JSONField(name = "scaled_tower_damage")
    private int scaledTowerDamage;
    /**
     * 该玩家的英雄治疗占比
     */
    @JSONField(name = "scaled_hero_healing")
    private int scaledHeroHealing;
    /**
     * 该玩家的技能升级情况
     */
    @JSONField(name = "ability_upgrades")
    private List<AbilityUpgradesBean> abilityUpgrades;
}
