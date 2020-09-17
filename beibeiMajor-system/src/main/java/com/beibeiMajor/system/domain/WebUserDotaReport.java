package com.beibeiMajor.system.domain;

import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

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

    /** 场均KDA */
    @Excel(name = "场均KDA")
    private Double kda;

    /** 当前连胜/连败次数 */
    @Excel(name = "当前连胜/连败次数")
    private Integer curMaxCount;

    /** 连胜/连败 */
    @Excel(name = "连胜/连败")
    private Boolean isWin;

    /** 最后比赛时间 */
    @Excel(name = "最后比赛时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastPlayTime;

    /** 场均击杀 */
    @Excel(name = "场均击杀")
    private Double averageKills;

    /** 场均阵亡 */
    @Excel(name = "场均阵亡")
    private Double averageDeaths;

    //积分排名
    private Integer rank;

    /** 场均助攻 */
    @Excel(name = "场均助攻")
    private Double averageAssists;

    /** GPM */
    @Excel(name = "GPM")
    private Integer goldPerMin;

    /** XPM */
    @Excel(name = "XPM")
    private Integer xpPerMin;

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

    public Double getKda() {
        return kda;
    }

    public void setKda(Double kda) {
        this.kda = kda;
    }

    public Double getAverageKills() {
        return averageKills;
    }

    public void setAverageKills(Double averageKills) {
        this.averageKills = averageKills;
    }

    public Double getAverageDeaths() {
        return averageDeaths;
    }

    public void setAverageDeaths(Double averageDeaths) {
        this.averageDeaths = averageDeaths;
    }

    public Double getAverageAssists() {
        return averageAssists;
    }

    public void setAverageAssists(Double averageAssists) {
        this.averageAssists = averageAssists;
    }

    public Integer getGoldPerMin() {
        return goldPerMin;
    }

    public void setGoldPerMin(Integer goldPerMin) {
        this.goldPerMin = goldPerMin;
    }

    public Integer getXpPerMin() {
        return xpPerMin;
    }

    public void setXpPerMin(Integer xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    @Override
    public String toString() {
        return "WebUserDotaReport{" +
                "userId=" + userId +
                ", integral=" + integral +
                ", winRate=" + winRate +
                ", mvpCount=" + mvpCount +
                ", kda=" + kda +
                ", curMaxCount=" + curMaxCount +
                ", isWin=" + isWin +
                ", lastPlayTime=" + lastPlayTime +
                ", averageKills=" + averageKills +
                ", averageDeaths=" + averageDeaths +
                ", averageAssists=" + averageAssists +
                ", goldPerMin=" + goldPerMin +
                ", xpPerMin=" + xpPerMin +
                '}';
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
