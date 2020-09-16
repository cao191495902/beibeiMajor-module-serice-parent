package com.beibeiMajor.web.service;

import com.beibeiMajor.web.service.dto.HeroBean;
import com.beibeiMajor.web.service.dto.MatchDetailDto;

import java.util.List;

public interface MatchDetailInfoService {

    MatchDetailDto getMatchDetailById(Long matchId);

    List<HeroBean> GetHeroesDetailInfo();

}
