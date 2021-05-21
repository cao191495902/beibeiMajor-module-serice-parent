package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.mapper.dao.*;
import com.beibeiMajor.web.mapper.po.*;
import com.beibeiMajor.web.service.OperationInfoToDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 */
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
    @Resource
    IWebUserService webUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean MatchsBatchIntoDB(List<WebMatchDetailPo> detailList, List<WebMatchPicksPo> picksList, List<WebMatchPlayerPo> playerList, List<WebAbilityUpgradesPo> abilityUpgradesList) {
        try {
            webMatchDetailDao.batchInsert(detailList);
            webMatchPicksDao.batchInsert(picksList);
            webMatchPlayerInfoDao.batchInsert(playerList);
            if(abilityUpgradesList != null && abilityUpgradesList.size() >0){
                webAbilityUpgradesDao.batchInsert(abilityUpgradesList);
            }
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
    public Boolean batchUpdateIntegralToDB(List<WebUserDotaReportPo> updateIntegralList, List<MatchPlayerIntegralPo> matchPlayerIntegralPoList, List<Long> updateMatchList) {
        try{
            //更新入库
            webUserDotaReportDao.batchUpdate(updateIntegralList);
            //修改比赛结算状态
            webMatchDetailDao.changeMatchStatus(updateMatchList);
            //更新比赛赛前赛后积分
            webMatchPlayerInfoDao.batchUpdatePlayerIntegral(matchPlayerIntegralPoList);
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
            if (!CollectionUtils.isEmpty(list)) {
                webMatchDetailDao.batchUpdateDoubleAccount(list);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                webDoubleIntegralRecordService.batchUpdateDoubleAccount(updateList);
            }
            return true;
        }catch (Exception e){
            log.error("batch update double account failed",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean rollbackDoubleIntegralRecord(Long accountId,Long times,Integer type,String remark) {
        try{
            WebUser webUser = webUserService.selectWebUserByAccountId(accountId);
            //更新记录
            WebDoubleIntegralRecord newRecord = new WebDoubleIntegralRecord();
            newRecord.setAccountId(accountId);
            newRecord.setChangeTimes(times);
            newRecord.setMoney(new BigDecimal(0));
            newRecord.setCreatedBy(webUser.getNickName());
            newRecord.setCreatedTime(System.currentTimeMillis() / 1000);
            newRecord.setSettlementStatus(true);
            newRecord.setType(type);
            newRecord.setRemark(remark);
            webDoubleIntegralRecordService.insertWebDoubleIntegralRecord(newRecord);
            //减去用户次数
            webUser.setDoubleIntegralTimes(webUser.getDoubleIntegralTimes() + times.intValue());
            webUserService.updateWebUser(webUser);
            return true;
        }catch (Exception e){
            log.error("rollback double integral record failed",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }
}
