package com.beibeiMajor.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

/**
 * 比赛玩家详情对象 web_match_player_info
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public class WebMatchPlayerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long accountId;

    /** 比赛ID */
    @Excel(name = "比赛ID")
    private Long matchId;

    /** $column.columnComment */
    @Excel(name = "比赛ID")
    private Long playerSlot;

    /** 英雄ID */
    @Excel(name = "英雄ID")
    private Long heroId;

    /** 左上方物品ID */
    @Excel(name = "左上方物品ID")
    private Long item0;

    /** 中间上方物品ID */
    @Excel(name = "中间上方物品ID")
    private Long item1;

    /** 右上方物品ID */
    @Excel(name = "右上方物品ID")
    private Long item2;

    /** 左下方物品ID */
    @Excel(name = "左下方物品ID")
    private Long item3;

    /** 中间下方物品ID */
    @Excel(name = "中间下方物品ID")
    private Long item4;

    /** 右下方物品ID */
    @Excel(name = "右下方物品ID")
    private Long item5;

    /** 备用库存物品1的ID */
    @Excel(name = "备用库存物品1的ID")
    private Long backpack0;

    /** 备用库存物品2的ID */
    @Excel(name = "备用库存物品2的ID")
    private Long backpack1;

    /** 备用库存物品3的ID */
    @Excel(name = "备用库存物品3的ID")
    private Long backpack2;

    /** 该玩家的击杀数 */
    @Excel(name = "该玩家的击杀数")
    private Long kills;

    /** 该玩家的死亡数 */
    @Excel(name = "该玩家的死亡数")
    private Long deaths;

    /** 该玩家的助攻数 */
    @Excel(name = "该玩家的助攻数")
    private Long assists;

    /** 该玩家的正补数 */
    @Excel(name = "该玩家的正补数")
    private Long lastHits;

    /** 该玩家的反补数 */
    @Excel(name = "该玩家的反补数")
    private Long denies;

    /** 该玩家的GPM */
    @Excel(name = "该玩家的GPM")
    private Long goldPerMin;

    /** 该玩家的XPM */
    @Excel(name = "该玩家的XPM")
    private Long xpPerMin;

    /** 该玩家的等级 */
    @Excel(name = "该玩家的等级")
    private Long level;

    /** 该玩家的英雄伤害 */
    @Excel(name = "该玩家的英雄伤害")
    private Long heroDamage;

    /** 该玩家的防御塔伤害 */
    @Excel(name = "该玩家的防御塔伤害")
    private Long towerDamage;

    /** 该玩家的治疗量 */
    @Excel(name = "该玩家的治疗量")
    private Long heroHealing;

    /** 该玩家的总金钱 */
    @Excel(name = "该玩家的总金钱")
    private Long gold;

    /** 该玩家花费的金钱 */
    @Excel(name = "该玩家花费的金钱")
    private Long goldSpent;

    /** 该玩家的英雄伤害占比 */
    @Excel(name = "该玩家的英雄伤害占比")
    private Long scaledHeroDamage;

    /** 该玩家的防御塔伤害占比 */
    @Excel(name = "该玩家的防御塔伤害占比")
    private Long scaledTowerDamage;

    /** 该玩家的英雄治疗占比 */
    @Excel(name = "该玩家的英雄治疗占比")
    private Long scaledHeroHealing;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setMatchId(Long matchId) 
    {
        this.matchId = matchId;
    }

    public Long getMatchId() 
    {
        return matchId;
    }
    public void setPlayerSlot(Long playerSlot) 
    {
        this.playerSlot = playerSlot;
    }

    public Long getPlayerSlot() 
    {
        return playerSlot;
    }
    public void setHeroId(Long heroId) 
    {
        this.heroId = heroId;
    }

    public Long getHeroId() 
    {
        return heroId;
    }
    public void setItem0(Long item0) 
    {
        this.item0 = item0;
    }

    public Long getItem0() 
    {
        return item0;
    }
    public void setItem1(Long item1) 
    {
        this.item1 = item1;
    }

    public Long getItem1() 
    {
        return item1;
    }
    public void setItem2(Long item2) 
    {
        this.item2 = item2;
    }

    public Long getItem2() 
    {
        return item2;
    }
    public void setItem3(Long item3) 
    {
        this.item3 = item3;
    }

    public Long getItem3() 
    {
        return item3;
    }
    public void setItem4(Long item4) 
    {
        this.item4 = item4;
    }

    public Long getItem4() 
    {
        return item4;
    }
    public void setItem5(Long item5) 
    {
        this.item5 = item5;
    }

    public Long getItem5() 
    {
        return item5;
    }
    public void setBackpack0(Long backpack0) 
    {
        this.backpack0 = backpack0;
    }

    public Long getBackpack0() 
    {
        return backpack0;
    }
    public void setBackpack1(Long backpack1) 
    {
        this.backpack1 = backpack1;
    }

    public Long getBackpack1() 
    {
        return backpack1;
    }
    public void setBackpack2(Long backpack2) 
    {
        this.backpack2 = backpack2;
    }

    public Long getBackpack2() 
    {
        return backpack2;
    }
    public void setKills(Long kills) 
    {
        this.kills = kills;
    }

    public Long getKills() 
    {
        return kills;
    }
    public void setDeaths(Long deaths) 
    {
        this.deaths = deaths;
    }

    public Long getDeaths() 
    {
        return deaths;
    }
    public void setAssists(Long assists) 
    {
        this.assists = assists;
    }

    public Long getAssists() 
    {
        return assists;
    }
    public void setLastHits(Long lastHits) 
    {
        this.lastHits = lastHits;
    }

    public Long getLastHits() 
    {
        return lastHits;
    }
    public void setDenies(Long denies) 
    {
        this.denies = denies;
    }

    public Long getDenies() 
    {
        return denies;
    }
    public void setGoldPerMin(Long goldPerMin) 
    {
        this.goldPerMin = goldPerMin;
    }

    public Long getGoldPerMin() 
    {
        return goldPerMin;
    }
    public void setXpPerMin(Long xpPerMin) 
    {
        this.xpPerMin = xpPerMin;
    }

    public Long getXpPerMin() 
    {
        return xpPerMin;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setHeroDamage(Long heroDamage) 
    {
        this.heroDamage = heroDamage;
    }

    public Long getHeroDamage() 
    {
        return heroDamage;
    }
    public void setTowerDamage(Long towerDamage) 
    {
        this.towerDamage = towerDamage;
    }

    public Long getTowerDamage() 
    {
        return towerDamage;
    }
    public void setHeroHealing(Long heroHealing) 
    {
        this.heroHealing = heroHealing;
    }

    public Long getHeroHealing() 
    {
        return heroHealing;
    }
    public void setGold(Long gold) 
    {
        this.gold = gold;
    }

    public Long getGold() 
    {
        return gold;
    }
    public void setGoldSpent(Long goldSpent) 
    {
        this.goldSpent = goldSpent;
    }

    public Long getGoldSpent() 
    {
        return goldSpent;
    }
    public void setScaledHeroDamage(Long scaledHeroDamage) 
    {
        this.scaledHeroDamage = scaledHeroDamage;
    }

    public Long getScaledHeroDamage() 
    {
        return scaledHeroDamage;
    }
    public void setScaledTowerDamage(Long scaledTowerDamage) 
    {
        this.scaledTowerDamage = scaledTowerDamage;
    }

    public Long getScaledTowerDamage() 
    {
        return scaledTowerDamage;
    }
    public void setScaledHeroHealing(Long scaledHeroHealing) 
    {
        this.scaledHeroHealing = scaledHeroHealing;
    }

    public Long getScaledHeroHealing() 
    {
        return scaledHeroHealing;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accountId", getAccountId())
            .append("matchId", getMatchId())
            .append("playerSlot", getPlayerSlot())
            .append("heroId", getHeroId())
            .append("item0", getItem0())
            .append("item1", getItem1())
            .append("item2", getItem2())
            .append("item3", getItem3())
            .append("item4", getItem4())
            .append("item5", getItem5())
            .append("backpack0", getBackpack0())
            .append("backpack1", getBackpack1())
            .append("backpack2", getBackpack2())
            .append("kills", getKills())
            .append("deaths", getDeaths())
            .append("assists", getAssists())
            .append("lastHits", getLastHits())
            .append("denies", getDenies())
            .append("goldPerMin", getGoldPerMin())
            .append("xpPerMin", getXpPerMin())
            .append("level", getLevel())
            .append("heroDamage", getHeroDamage())
            .append("towerDamage", getTowerDamage())
            .append("heroHealing", getHeroHealing())
            .append("gold", getGold())
            .append("goldSpent", getGoldSpent())
            .append("scaledHeroDamage", getScaledHeroDamage())
            .append("scaledTowerDamage", getScaledTowerDamage())
            .append("scaledHeroHealing", getScaledHeroHealing())
            .toString();
    }
}
