package com.beibeiMajor.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

/**
 * 比赛bp对象 web_match_picks
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public class WebMatchPicks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 比赛Id */
    private Long matchId;

    /** B/P */
    @Excel(name = "B/P")
    private Integer isPick;

    /** 英雄Id */
    @Excel(name = "英雄Id")
    private Long heroId;

    /** 阵营 */
    @Excel(name = "阵营")
    private Integer team;

    /** 选择顺序 */
    @Excel(name = "选择顺序")
    private Long order;

    public void setMatchId(Long matchId) 
    {
        this.matchId = matchId;
    }

    public Long getMatchId() 
    {
        return matchId;
    }
    public void setIsPick(Integer isPick) 
    {
        this.isPick = isPick;
    }

    public Integer getIsPick() 
    {
        return isPick;
    }
    public void setHeroId(Long heroId) 
    {
        this.heroId = heroId;
    }

    public Long getHeroId() 
    {
        return heroId;
    }
    public void setTeam(Integer team) 
    {
        this.team = team;
    }

    public Integer getTeam() 
    {
        return team;
    }
    public void setOrder(Long order) 
    {
        this.order = order;
    }

    public Long getOrder() 
    {
        return order;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("matchId", getMatchId())
            .append("isPick", getIsPick())
            .append("heroId", getHeroId())
            .append("team", getTeam())
            .append("order", getOrder())
            .toString();
    }
}
