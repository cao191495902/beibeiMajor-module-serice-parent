package com.beibeiMajor.web.service;

import java.util.List;

public interface DotaGameInfoService {

    List<Long> getGameIdOfLeagueMatch();

    Boolean insertMatchDetailInfo(List<Long> gameIdOfLeagueMatch);

    Boolean insertHeroesInfo();

    void updateUserInfo();
}
