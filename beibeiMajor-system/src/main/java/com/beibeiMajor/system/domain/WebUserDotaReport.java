package com.beibeiMajor.system.domain;

import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户积分对象 web_user_dota_report
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
public class WebUserDotaReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    private Long nickName;

    /** 积分 */
    @Excel(name = "积分")
    private Long integral;

    /** 胜率 */
    @Excel(name = "胜率")
    private BigDecimal winRate;

    /** mvp次数 */
    @Excel(name = "mvp次数")
    private Long mvpCount;

    /** 场均KDA */
    @Excel(name = "场均KDA")
    private Long kda;

    /** 最高连胜次数 */
    @Excel(name = "最高连胜次数")
    private Long maxWinCount;

    /** 最高连败次数 */
    @Excel(name = "最高连败次数")
    private Long maxLoseCount;

    /** 最后比赛时间 */
    @Excel(name = "最后比赛时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastPlayTime;

    /** 场均击杀 */
    @Excel(name = "场均击杀")
    private Long averageKills;

    /** 场均阵亡 */
    @Excel(name = "场均阵亡")
    private Long averageDeaths;

    /** 场均助攻 */
    @Excel(name = "场均助攻")
    private Long averageAssists;

    /** GPM */
    @Excel(name = "GPM")
    private Long goldPerMin;

    /** XPM */
    @Excel(name = "XPM")
    private Long xpPerMin;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setIntegral(Long integral) 
    {
        this.integral = integral;
    }

    public Long getIntegral() 
    {
        return integral;
    }
    public void setWinRate(BigDecimal winRate) 
    {
        this.winRate = winRate;
    }

    public BigDecimal getWinRate() 
    {
        return winRate;
    }
    public void setMvpCount(Long mvpCount) 
    {
        this.mvpCount = mvpCount;
    }

    public Long getMvpCount() 
    {
        return mvpCount;
    }
    public void setKda(Long kda) 
    {
        this.kda = kda;
    }

    public Long getKda() 
    {
        return kda;
    }
    public void setMaxWinCount(Long maxWinCount) 
    {
        this.maxWinCount = maxWinCount;
    }

    public Long getMaxWinCount() 
    {
        return maxWinCount;
    }
    public void setMaxLoseCount(Long maxLoseCount) 
    {
        this.maxLoseCount = maxLoseCount;
    }

    public Long getMaxLoseCount() 
    {
        return maxLoseCount;
    }
    public void setLastPlayTime(Date lastPlayTime) 
    {
        this.lastPlayTime = lastPlayTime;
    }

    public Date getLastPlayTime() 
    {
        return lastPlayTime;
    }
    public void setAverageKills(Long averageKills) 
    {
        this.averageKills = averageKills;
    }

    public Long getAverageKills() 
    {
        return averageKills;
    }
    public void setAverageDeaths(Long averageDeaths) 
    {
        this.averageDeaths = averageDeaths;
    }

    public Long getAverageDeaths() 
    {
        return averageDeaths;
    }
    public void setAverageAssists(Long averageAssists) 
    {
        this.averageAssists = averageAssists;
    }

    public Long getAverageAssists() 
    {
        return averageAssists;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("integral", getIntegral())
            .append("winRate", getWinRate())
            .append("mvpCount", getMvpCount())
            .append("kda", getKda())
            .append("maxWinCount", getMaxWinCount())
            .append("maxLoseCount", getMaxLoseCount())
            .append("lastPlayTime", getLastPlayTime())
            .append("averageKills", getAverageKills())
            .append("averageDeaths", getAverageDeaths())
            .append("averageAssists", getAverageAssists())
            .append("goldPerMin", getGoldPerMin())
            .append("xpPerMin", getXpPerMin())
            .toString();
    }

    public Long getNickName() {
        return nickName;
    }

    public void setNickName(Long nickName) {
        this.nickName = nickName;
    }
}
