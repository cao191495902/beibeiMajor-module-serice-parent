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
    @Excel(name = "当前连胜/连败次数")
    private Integer curMaxCount;

    /** 最高连败次数 */
    @Excel(name = "连胜/连败")
    private Boolean isWin;

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

    public Integer getCurMaxCount() {
        return curMaxCount;
    }

    public void setCurMaxCount(Integer curMaxCount) {
        this.curMaxCount = curMaxCount;
    }

    public Boolean getWin() {
        return isWin;
    }

    public void setWin(Boolean win) {
        isWin = win;
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
            .append("curMaxCount", getCurMaxCount())
            .append("isWin", getWin())
            .append("lastPlayTime", getLastPlayTime())
            .toString();
    }
}
