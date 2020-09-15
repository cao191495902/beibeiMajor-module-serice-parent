package com.beibeiMajor.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

/**
 * 比赛玩家加点记录对象 web_ability_upgrades
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public class WebAbilityUpgrades extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 比赛Id */
    @Excel(name = "比赛Id")
    private Long matchId;

    /** 用户Id */
    @Excel(name = "用户Id")
    private Long accountId;

    /** 技能Id */
    @Excel(name = "技能Id")
    private Long abilityId;

    /** 加点时间 */
    @Excel(name = "加点时间")
    private Long time;

    /** 玩家等级 */
    @Excel(name = "玩家等级")
    private Long level;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMatchId(Long matchId) 
    {
        this.matchId = matchId;
    }

    public Long getMatchId() 
    {
        return matchId;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setAbilityId(Long abilityId) 
    {
        this.abilityId = abilityId;
    }

    public Long getAbilityId() 
    {
        return abilityId;
    }
    public void setTime(Long time) 
    {
        this.time = time;
    }

    public Long getTime() 
    {
        return time;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("matchId", getMatchId())
            .append("accountId", getAccountId())
            .append("abilityId", getAbilityId())
            .append("time", getTime())
            .append("level", getLevel())
            .toString();
    }
}
