package com.beibeiMajor.service;

import com.beibeiMajor.mapper.po.WebAbilityUpgradesPo;
import com.beibeiMajor.mapper.po.WebMatchDetailPo;
import com.beibeiMajor.mapper.po.WebMatchPicksPo;
import com.beibeiMajor.mapper.po.WebMatchPlayerPo;
import com.beibeiMajor.service.dto.HeroBean;
import com.beibeiMajor.service.dto.MatchDetailDto;

import java.util.List;

public interface MatchDetailInfoService {

    MatchDetailDto getMatchDetailById(Long matchId);

    List<HeroBean> GetHeroesDetailInfo();

}
