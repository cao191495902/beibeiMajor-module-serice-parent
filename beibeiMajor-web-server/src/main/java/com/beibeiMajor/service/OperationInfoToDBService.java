package com.beibeiMajor.service;

import com.beibeiMajor.mapper.po.*;

import java.util.List;

public interface OperationInfoToDBService {
    Boolean MatchsBatchIntoDB(List<WebMatchDetailPo> detailList, List<WebMatchPicksPo> picksList, List<WebMatchPlayerPo> playerList, List<WebAbilityUpgradesPo> abilityUpgradesList);

    Boolean HeroesBatchIntoDB(List<WebDotaHeroPo> lists);
}
