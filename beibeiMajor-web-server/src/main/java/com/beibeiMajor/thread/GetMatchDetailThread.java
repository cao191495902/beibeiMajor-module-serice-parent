package com.beibeiMajor.thread;

import com.beibeiMajor.service.MatchDetailInfoService;
import com.beibeiMajor.service.dto.MatchDetailDto;

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
        return matchDetailInfoService.getMatchDetailById(matchId);
    }
}
