package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * (WebMatchDetail)实体类
 *
 * @author makejava
 * @since 2020-09-14 10:47:41
 */
@Data
public class WebMatchDetailPo implements Serializable {
    private static final long serialVersionUID = -92905572366401030L;
    /**
     * 比赛ID
     */
    private Long matchId;
    /**
     * 联赛ID
     */
    private Long leagueId;
    /**
     * 获胜方
     */
    private Boolean radiantWin;
    /**
     * 比赛长度
     */
    private Long duration;
    /**
     * 比赛B/P时间
     */
    private Long preGameDuration;
    /**
     * 比赛开始时间
     */
    private Long startTime;
    /**
     * 天辉防御塔状态
     */
    private Long towerStatusRadiant;
    /**
     * 夜宴防御塔状态
     */
    private Long towerStatusDire;
    /**
     * 天辉兵营状态
     */
    private Long barracksStatusRadiant;
    /**
     * 夜宴兵营状态
     */
    private Long barracksStatusDire;
    /**
     * 第一滴血发生时间
     */
    private Long firstBloodTime;
    /**
     * 玩家数量
     */
    private int humanPlayers;
    /**
     * 比赛类型
     */
    private Integer gameMode;
    /**
     * 天辉得分
     */
    private Integer radiantScore;
    /**
     * 夜宴得分
     */
    private Integer direScore;
    /**
     * 天辉队长
     */
    private Integer radiantCaptain;
    /**
     * 夜宴队长
     */
    private Integer direCaptain;
    /**
     * 结算状态
     */
    private Boolean settlementStatus;
    /**
     * 双倍用户
     */
    private String doubleAccount;
}