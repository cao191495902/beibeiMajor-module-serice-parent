package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.mapper.dao.WebMatchDetailDao;
import com.beibeiMajor.web.mapper.dao.WebMatchPlayerInfoDao;
import com.beibeiMajor.web.mapper.dao.WebUserDotaReportDao;
import com.beibeiMajor.web.mapper.po.UserAverageDataPo;
import com.beibeiMajor.web.mapper.po.WebMatchDetailPo;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;
import com.beibeiMajor.web.mapper.po.WinAndLosePo;
import com.beibeiMajor.web.service.OperationInfoToDBService;
import com.beibeiMajor.web.service.ReportInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lenovo
 */
@Service
@Slf4j
public class ReportInfoServiceImpl implements ReportInfoService {

    @Resource
    WebUserDotaReportDao webUserDotaReportDao;
    @Resource
    WebMatchDetailDao webMatchDetailDao;
    @Resource
    WebMatchPlayerInfoDao webMatchPlayerInfoDao;
    @Resource
    IWebUserService webUserService;
    @Resource
    OperationInfoToDBService operationInfoToDBService;

    @Override
    public List<WebUserDotaReportPo> handlerMatchInfoToReport() {
        //STEP 0 更新用户报表
        List<WebUserDotaReportPo> reportPoList = updateDotaReportInfo();
        List<WinAndLosePo> list = webUserDotaReportDao.getUserWinAndLose();
        if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(reportPoList)){
            //STEP 1 计算用户积分
            handlerIntegralOfUsers();
            //STEP 2 计算用户胜率
            List<WebUserDotaReportPo> nowReportPoList = webUserDotaReportDao.queryAll(new WebUserDotaReportPo());
            list.forEach(item->{
                nowReportPoList.forEach(reportPo -> {
                    if (reportPo.getUserId().longValue() == item.getAccountId().longValue()){
                        reportPo.setWinRate(item.getWinRate());
                    }
                });
            });
            //STEP 3 场均KDA,场均击杀,场均阵亡,场均助攻,场均GPM,场均XPM
            List<UserAverageDataPo> avgDataList = webUserDotaReportDao.getUserAverageData();
            avgDataList.forEach(item -> nowReportPoList.forEach(reportPo -> {
                if (reportPo.getUserId().longValue() == item.getAccountId().longValue()){
                    reportPo.setAverageAssists(item.getAssist());
                    reportPo.setAverageDeaths(item.getDeath());
                    reportPo.setAverageKills(item.getKill());
                    reportPo.setKda(item.getKda());
                    reportPo.setXpPerMin(item.getXpm());
                    reportPo.setGoldPerMin(item.getGpm());
                }
            }));
            //STEP 4 最后玩耍时间,当前连胜,当前连败
            Map<Long,Long> lastPlayTimeMap = webMatchDetailDao.getLastPlayTime();
            for (Map.Entry<Long, Long> m : lastPlayTimeMap.entrySet()) {
                nowReportPoList.forEach(reportPo -> {
                    if (m.getKey().longValue() == reportPo.getUserId().longValue()){
                        reportPo.setLastPlayTime(new Date(m.getValue()));
                    }
                });
            }

            //TODO STEP 5 MVP
            //全量更新表
            webUserDotaReportDao.batchUpdate(nowReportPoList);
            reportPoList = nowReportPoList;
        }
        return reportPoList;
    }

    private List<WebUserDotaReportPo> updateDotaReportInfo() {
        List<WebUserDotaReportPo> reportPoList = webUserDotaReportDao.queryAll(new WebUserDotaReportPo());
        List<WebUser> webUsers = webUserService.selectWebUserList(new WebUser());
        if (webUsers.size() > reportPoList.size()){
            if (CollectionUtils.isNotEmpty(reportPoList)){
                Iterator<WebUser> iterator = webUsers.iterator();
                while (iterator.hasNext()) {
                    WebUser next = iterator.next();
                    reportPoList.forEach(webUserDotaReportPo -> {
                        if (next.getAccountId().equals(webUserDotaReportPo.getUserId()))
                            //使用迭代器的删除方法删除
                            iterator.remove();
                    });
                }
            }
            webUsers.forEach(webUser -> {
                WebUserDotaReportPo reportPo = new WebUserDotaReportPo();
                reportPo.setUserId(webUser.getAccountId());
                reportPo.setIntegral(3000);
                reportPoList.add(reportPo);
            });
            try{
                webUserDotaReportDao.batchInsert(reportPoList);
            }catch (Exception e){
                log.error("batch insert new report info failed", e.getMessage());
            }
            return reportPoList;
        }
        return reportPoList;
    }

    public Boolean handlerIntegralOfUsers(){
        //step 1 按照每把比赛进行计算
        List<WebMatchDetailPo> webMatchDetailPos = webMatchDetailDao.queryAllUnsettledMatch();
        webMatchDetailPos.forEach(webMatchDetailPo -> {
            List<WebUserDotaReportPo> updateIntegralList = new ArrayList<>();
            List<WebUserDotaReportPo> reportPoList = webUserDotaReportDao.queryAll(new WebUserDotaReportPo());
            List<Long> winPlayerID = webMatchPlayerInfoDao.getWinPlayerByMatchId(webMatchDetailPo.getMatchId());
            List<Long> losePlayerID = webMatchPlayerInfoDao.getLosePlayerByMatchId(webMatchDetailPo.getMatchId());
            List<Integer> winList = new ArrayList<>();
            List<Integer> loseList = new ArrayList<>();
            winPlayerID.forEach(winPlayerId -> reportPoList.forEach(reportPo->{
                if (reportPo.getUserId().equals(winPlayerId)){
                    winList.add(reportPo.getIntegral());
                }
            }));
            int winSum = winList.stream().reduce(Integer::sum).orElse(0);
            losePlayerID.forEach(losePlayerId -> reportPoList.forEach(reportPo->{
                if (reportPo.getUserId().equals(losePlayerId)){
                    loseList.add(reportPo.getIntegral());
                }
            }));
            int loseSum = loseList.stream().reduce(Integer::sum).orElse(0);
            Integer fraction = 0;
            if (winSum > loseSum){
                int disparity = (winSum - loseSum) / 1000;
                fraction = 100 - disparity*10;
            }else{
                int disparity = (loseSum - winSum) / 1000;
                fraction = 100 + disparity*10;
            }
            Integer finalFraction = fraction;
            Integer finalFraction1 = fraction;
            winPlayerID.forEach(item -> reportPoList.stream().filter(webUserDotaReportPo -> webUserDotaReportPo.getUserId().longValue() == item.longValue()).forEach(webUserDotaReportPo -> {
                Integer integral = webUserDotaReportPo.getIntegral();
                int count = integral + finalFraction;
                webUserDotaReportPo.setIntegral(count);
                updateIntegralList.add(webUserDotaReportPo);
            }));
            losePlayerID.forEach(item -> reportPoList.stream().filter(webUserDotaReportPo -> webUserDotaReportPo.getUserId().longValue() == item.longValue()).forEach(webUserDotaReportPo -> {
                Integer integral = webUserDotaReportPo.getIntegral();
                int count = integral - finalFraction1;
                webUserDotaReportPo.setIntegral(count);
                updateIntegralList.add(webUserDotaReportPo);
            }));
            operationInfoToDBService.batchUpdateIntegralToDB(updateIntegralList, webMatchDetailPo);
        });
        return true;
    }

}
