package com.beibeiMajor.web.service;

import com.beibeiMajor.web.mapper.po.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OperationInfoToDBService {
    Boolean MatchsBatchIntoDB(List<WebMatchDetailPo> detailList, List<WebMatchPicksPo> picksList, List<WebMatchPlayerPo> playerList, List<WebAbilityUpgradesPo> abilityUpgradesList);

    Boolean HeroesBatchIntoDB(List<WebDotaHeroPo> lists);

    @Transactional(rollbackFor = Exception.class)
    Boolean batchUpdateIntegralToDB(List<WebUserDotaReportPo> updateIntegralList, List<MatchPlayerIntegralPo> matchPlayerIntegralPoList, List<Long> updateMatchList);

    Boolean batchUpdateDoubleAccount(Map<Long, String> accMap, List<Long> updateList);

    Boolean rollbackDoubleIntegralRecord(Long accountId);
}
