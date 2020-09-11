package com.beibeiMajor.service;

import com.beibeiMajor.service.dto.MatchDetailDto;

public interface MatchDetailInfoService {

    MatchDetailDto getMatchDetailById(Long matchId);
}
