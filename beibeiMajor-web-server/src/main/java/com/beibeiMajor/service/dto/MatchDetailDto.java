package com.beibeiMajor.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author lenovo
 */
@Data
public class MatchDetailDto {

    /**
     * 获胜方
     */
    @JSONField(name = "radiant_win")
    private boolean radiantWin;
    /**
     * 比赛长度
     */
    @JSONField(name = "duration")
    private Long duration;
    /**
     * 比赛B/P时间
     */
    @JSONField(name = "pre_game_duration")
    private Long preGameDuration;
    /**
     * 比赛开始时间
     */
    @JSONField(name = "start_time")
    private Long startTime;
    /**
     * 比赛ID
     */
    @JSONField(name = "match_id")
    private Long matchId;

    @JSONField(name = "match_seq_num")
    private long matchSeqNum;
    /**
     * 天辉防御塔状态
     */
    @JSONField(name = "tower_status_radiant")
    private Long towerStatusRadiant;
    /**
     * 夜宴防御塔状态
     */
    @JSONField(name = "tower_status_dire")
    private Long towerStatusDire;
    /**
     * 天辉兵营状态
     */
    @JSONField(name = "barracks_status_radiant")
    private Long barracksStatusRadiant;
    /**
     * 夜宴兵营状态
     */
    @JSONField(name = "barracks_status_dire")
    private Long barracksStatusDire;

    @JSONField(name = "cluster")
    private int cluster;
    /**
     * 第一滴血发生时间
     */
    @JSONField(name = "first_blood_time")
    private Long firstBloodTime;

    @JSONField(name = "lobby_type")
    private int lobbyType;
    /**
     * 玩家数量
     */
    @JSONField(name = "human_players")
    private int humanPlayers;
    /**
     * 联赛ID
     */
    @JSONField(name = "leagueid")
    private Long leagueId;

    @JSONField(name = "positive_votes")
    private int positiveVotes;

    @JSONField(name = "negative_votes")
    private int negativeVotes;
    /**
     * 比赛类型
     */
    @JSONField(name = "game_mode")
    private int gameMode;

    @JSONField(name = "flags")
    private int flags;

    @JSONField(name = "engine")
    private int engine;
    /**
     * 天辉得分
     */
    @JSONField(name = "radiant_score")
    private int radiantScore;
    /**
     * 夜宴得分
     */
    @JSONField(name = "dire_score")
    private int direScore;
    /**
     * 天辉队长
     */
    @JSONField(name = "radiant_captain")
    private int radiantCaptain;
    /**
     * 夜宴队长
     */
    @JSONField(name = "dire_captain")
    private int direCaptain;
    private List<PlayersBean> players;
    private List<PicksBansBean> picks_bans;
}
