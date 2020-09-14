package com.beibeiMajor.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

/**
 * 比赛详情对象 web_match_detail
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public class WebMatchDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 比赛ID */
    private Long matchId;

    /** 联赛ID */
    @Excel(name = "联赛ID")
    private Long leagueId;

    /** 获胜方 */
    @Excel(name = "获胜方")
    private Integer radiantWin;

    /** 比赛长度 */
    @Excel(name = "比赛长度")
    private Long duration;

    /** 比赛B/P时间 */
    @Excel(name = "比赛B/P时间")
    private Long preGameDuration;

    /** 比赛开始时间 */
    @Excel(name = "比赛开始时间")
    private Long startTime;

    /** 天辉防御塔状态 */
    @Excel(name = "天辉防御塔状态")
    private Long towerStatusRadiant;

    /** 夜宴防御塔状态 */
    @Excel(name = "夜宴防御塔状态")
    private Long towerStatusDire;

    /** 天辉兵营状态 */
    @Excel(name = "天辉兵营状态")
    private Long barracksStatusRadiant;

    /** 夜宴兵营状态 */
    @Excel(name = "夜宴兵营状态")
    private Long barracksStatusDire;

    /** 第一滴血发生时间 */
    @Excel(name = "第一滴血发生时间")
    private Long firstBloodTime;

    /** 玩家数量 */
    @Excel(name = "玩家数量")
    private Long humanPlayers;

    /** 比赛类型 */
    @Excel(name = "比赛类型")
    private Long gameMode;

    /** 天辉得分 */
    @Excel(name = "天辉得分")
    private Long radiantScore;

    /** 夜宴得分 */
    @Excel(name = "夜宴得分")
    private Long direScore;

    /** 天辉队长 */
    @Excel(name = "天辉队长")
    private Long radiantCaptain;

    /** 夜宴队长 */
    @Excel(name = "夜宴队长")
    private Long direCaptain;

    public void setMatchId(Long matchId) 
    {
        this.matchId = matchId;
    }

    public Long getMatchId() 
    {
        return matchId;
    }
    public void setLeagueId(Long leagueId) 
    {
        this.leagueId = leagueId;
    }

    public Long getLeagueId() 
    {
        return leagueId;
    }
    public void setRadiantWin(Integer radiantWin) 
    {
        this.radiantWin = radiantWin;
    }

    public Integer getRadiantWin() 
    {
        return radiantWin;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setPreGameDuration(Long preGameDuration) 
    {
        this.preGameDuration = preGameDuration;
    }

    public Long getPreGameDuration() 
    {
        return preGameDuration;
    }
    public void setStartTime(Long startTime) 
    {
        this.startTime = startTime;
    }

    public Long getStartTime() 
    {
        return startTime;
    }
    public void setTowerStatusRadiant(Long towerStatusRadiant) 
    {
        this.towerStatusRadiant = towerStatusRadiant;
    }

    public Long getTowerStatusRadiant() 
    {
        return towerStatusRadiant;
    }
    public void setTowerStatusDire(Long towerStatusDire) 
    {
        this.towerStatusDire = towerStatusDire;
    }

    public Long getTowerStatusDire() 
    {
        return towerStatusDire;
    }
    public void setBarracksStatusRadiant(Long barracksStatusRadiant) 
    {
        this.barracksStatusRadiant = barracksStatusRadiant;
    }

    public Long getBarracksStatusRadiant() 
    {
        return barracksStatusRadiant;
    }
    public void setBarracksStatusDire(Long barracksStatusDire) 
    {
        this.barracksStatusDire = barracksStatusDire;
    }

    public Long getBarracksStatusDire() 
    {
        return barracksStatusDire;
    }
    public void setFirstBloodTime(Long firstBloodTime) 
    {
        this.firstBloodTime = firstBloodTime;
    }

    public Long getFirstBloodTime() 
    {
        return firstBloodTime;
    }
    public void setHumanPlayers(Long humanPlayers) 
    {
        this.humanPlayers = humanPlayers;
    }

    public Long getHumanPlayers() 
    {
        return humanPlayers;
    }
    public void setGameMode(Long gameMode) 
    {
        this.gameMode = gameMode;
    }

    public Long getGameMode() 
    {
        return gameMode;
    }
    public void setRadiantScore(Long radiantScore) 
    {
        this.radiantScore = radiantScore;
    }

    public Long getRadiantScore() 
    {
        return radiantScore;
    }
    public void setDireScore(Long direScore) 
    {
        this.direScore = direScore;
    }

    public Long getDireScore() 
    {
        return direScore;
    }
    public void setRadiantCaptain(Long radiantCaptain) 
    {
        this.radiantCaptain = radiantCaptain;
    }

    public Long getRadiantCaptain() 
    {
        return radiantCaptain;
    }
    public void setDireCaptain(Long direCaptain) 
    {
        this.direCaptain = direCaptain;
    }

    public Long getDireCaptain() 
    {
        return direCaptain;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("matchId", getMatchId())
            .append("leagueId", getLeagueId())
            .append("radiantWin", getRadiantWin())
            .append("duration", getDuration())
            .append("preGameDuration", getPreGameDuration())
            .append("startTime", getStartTime())
            .append("towerStatusRadiant", getTowerStatusRadiant())
            .append("towerStatusDire", getTowerStatusDire())
            .append("barracksStatusRadiant", getBarracksStatusRadiant())
            .append("barracksStatusDire", getBarracksStatusDire())
            .append("firstBloodTime", getFirstBloodTime())
            .append("humanPlayers", getHumanPlayers())
            .append("gameMode", getGameMode())
            .append("radiantScore", getRadiantScore())
            .append("direScore", getDireScore())
            .append("radiantCaptain", getRadiantCaptain())
            .append("direCaptain", getDireCaptain())
            .toString();
    }
}
