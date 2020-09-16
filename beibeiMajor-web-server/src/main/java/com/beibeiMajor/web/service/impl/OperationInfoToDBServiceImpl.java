package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.web.service.OperationInfoToDBService;
import com.beibeiMajor.web.mapper.dao.*;
import com.beibeiMajor.web.mapper.po.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class OperationInfoToDBServiceImpl implements OperationInfoToDBService {

    @Resource
    private WebAbilityUpgradesDao webAbilityUpgradesDao;
    @Resource
    private WebMatchDetailDao webMatchDetailDao;
    @Resource
    private WebMatchPicksDao webMatchPicksDao;
    @Resource
    private WebMatchPlayerInfoDao webMatchPlayerInfoDao;
    @Resource
    private WebDotaHeroDao webDotaHeroDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean MatchsBatchIntoDB(List<WebMatchDetailPo> detailList, List<WebMatchPicksPo> picksList, List<WebMatchPlayerPo> playerList, List<WebAbilityUpgradesPo> abilityUpgradesList) {
        try {
            webMatchDetailDao.batchInsert(detailList);
            webMatchPicksDao.batchInsert(picksList);
            webMatchPlayerInfoDao.batchInsert(playerList);
            webAbilityUpgradesDao.batchInsert(abilityUpgradesList);
            return true;
        }catch (Exception e) {
            log.error("batch insert to DB failed!" + e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    public Boolean HeroesBatchIntoDB(List<WebDotaHeroPo> lists) {
        try {
            webDotaHeroDao.batchInsert(lists);
            return true;
        }catch (Exception e) {
            log.error("batch insert to DB failed!" + e.getMessage());
        }
        return false;
    }
}
