package com.beibeiMajor.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class MatchDto {

    @JSONField(name = "series_id")
    private int seriesId;
    @JSONField(name = "series_type")
    private int seriesType;
    @JSONField(name = "match_id")
    private long matchId;
    @JSONField(name = "match_seq_num")
    private long matchSeqNum;
    @JSONField(name = "start_time")
    private int startTime;
    @JSONField(name = "lobby_type")
    private int lobbyType;
    @JSONField(name = "radiant_team_id")
    private int radiantTeamId;
    @JSONField(name = "dire_team_id")
    private int direTeamId;
    private List<PlayersBean> players;
}
