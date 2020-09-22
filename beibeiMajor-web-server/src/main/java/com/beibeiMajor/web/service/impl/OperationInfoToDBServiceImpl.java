package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.web.mapper.dao.*;
import com.beibeiMajor.web.mapper.po.*;
import com.beibeiMajor.web.service.OperationInfoToDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    @Resource
    WebUserDotaReportDao webUserDotaReportDao;
    @Resource
    IWebDoubleIntegralRecordService webDoubleIntegralRecordService;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchUpdateIntegralToDB(List<WebUserDotaReportPo> updateIntegralList, WebMatchDetailPo webMatchDetailPo) {
        try{
            //更新入库
            webUserDotaReportDao.batchUpdate(updateIntegralList);
            //修改比赛结算状态
            webMatchDetailDao.changeMatchStatus(webMatchDetailPo.getMatchId());
            return true;
        }catch (Exception e){
            log.error("batch update integral failed",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchUpdateDoubleAccount(Map<Long, String> accMap, List<Long> updateList) {
        try{
            List<DoubleAccountPo> list = new ArrayList<>();
            for(Map.Entry<Long, String> entry : accMap.entrySet()){
                Long mapKey = entry.getKey();
                String mapValue = entry.getValue();
                DoubleAccountPo doubleAccountPo = new DoubleAccountPo();
                doubleAccountPo.setMatchId(mapKey);
                doubleAccountPo.setAccountIds(mapValue);
                list.add(doubleAccountPo);
            }
            webMatchDetailDao.batchUpdateDoubleAccount(list);
            webDoubleIntegralRecordService.batchUpdateDoubleAccount(updateList);
        }catch (Exception e){
            log.error("batch update double account failed",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }
}
