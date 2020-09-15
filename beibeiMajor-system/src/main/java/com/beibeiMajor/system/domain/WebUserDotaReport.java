package com.beibeiMajor.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

/**
 * 用户积分对象 web_user_dota_report
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public class WebUserDotaReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 积分 */
    @Excel(name = "积分")
    private Long integral;

    /** 胜率 */
    @Excel(name = "胜率")
    private BigDecimal winRate;

    /** mvp次数 */
    @Excel(name = "mvp次数")
    private Long mvpCount;

    /** 最高连胜次数 */
    @Excel(name = "最高连胜次数")
    private Long maxWinCount;

    /** 最高连败次数 */
    @Excel(name = "最高连败次数")
    private Long maxLoseCount;

    /** 最后比赛时间 */
    @Excel(name = "最后比赛时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastPlayTime;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("integral", getIntegral())
            .append("winRate", getWinRate())
            .append("mvpCount", getMvpCount())
            .append("maxWinCount", getMaxWinCount())
            .append("maxLoseCount", getMaxLoseCount())
            .append("lastPlayTime", getLastPlayTime())
            .toString();
    }
}
