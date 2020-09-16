package com.beibeiMajor.web.thread;

import com.beibeiMajor.web.service.MatchDetailInfoService;
import com.beibeiMajor.web.service.dto.MatchDetailDto;
import com.beibeiMajor.web.service.dto.PlayersBean;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author lenovo
 */
public class GetMatchDetailThread implements Callable<MatchDetailDto> {

    private MatchDetailInfoService matchDetailInfoService;
    private Long matchId;

    public GetMatchDetailThread(MatchDetailInfoService matchDetailInfoService, Long matchId){
        this.matchDetailInfoService = matchDetailInfoService;
        this.matchId = matchId;
    }

    @Override
    public MatchDetailDto call() throws Exception{
        MatchDetailDto matchDetailById = matchDetailInfoService.getMatchDetailById(matchId);
        List<PlayersBean> players = matchDetailById.getPlayers();
        int count= (int) players.stream().filter(item -> item.getLeaverStatus() != 0).count();
        if (count > 0){
            return null;
        }
        return matchDetailById;
    }
}
