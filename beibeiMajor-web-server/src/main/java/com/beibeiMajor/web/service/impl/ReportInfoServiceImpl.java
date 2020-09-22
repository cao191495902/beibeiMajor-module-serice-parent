package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.mapper.dao.WebMatchDetailDao;
import com.beibeiMajor.web.mapper.dao.WebMatchPlayerInfoDao;
import com.beibeiMajor.web.mapper.dao.WebUserDotaReportDao;
import com.beibeiMajor.web.mapper.po.*;
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
    @Resource
    IWebDoubleIntegralRecordService webDoubleIntegralRecordService;

    @Override
    public List<WebUserDotaReportPo> handlerMatchInfoToReport() {
        //STEP 0 更新用户报表以及绑定双倍比赛Id
        List<WebUserDotaReportPo> reportPoList = updateDotaReportInfo();
        bindingDoubleAccountToMatch();
        List<WinAndLosePo> list = webUserDotaReportDao.getUserWinAndLose();
        if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(reportPoList)){
            //STEP 1 计算用户积分
            handlerIntegralOfUsers();
            //STEP 2 计算用户胜率
            List<WebUserDotaReportPo> nowReportPoList = webUserDotaReportDao.queryAll(new WebUserDotaReportPo());
            list.forEach(item-> nowReportPoList.forEach(reportPo -> {
                if (reportPo.getUserId().longValue() == item.getAccountId().longValue()){
                    reportPo.setWinRate(item.getWinRate());
                    Integer totalNum = item.getWin() + item.getLose();
                    reportPo.setTotalMatchesNum(totalNum);
                }
            }));
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
            List<Map<Long,Long>> lastPlayTimeList = webMatchDetailDao.getLastPlayTime();
            lastPlayTimeList.forEach(mapBean -> nowReportPoList.stream().filter(reportPo -> mapBean.get("accountId").longValue() == reportPo.getUserId().longValue()).forEach(reportPo -> {
                Date date = new Date();
                date.setTime(mapBean.get("playTime")*1000);
                reportPo.setLastPlayTime(date);
            }));
            List<WinsOrLoseCountPo> countList = webMatchPlayerInfoDao.getWinsOrLoseCount();
            nowReportPoList.forEach(reportPo -> {
                countList.forEach(winsOrLoseCountPo -> {
                    if (reportPo.getUserId().longValue() == winsOrLoseCountPo.getAccountId()){
                        reportPo.setIsWin(winsOrLoseCountPo.getIsWin());
                        reportPo.setCurMaxCount(winsOrLoseCountPo.getCount());
                    }
                });
            });
            //TODO STEP 5 MVP
            //全量更新表
            webUserDotaReportDao.batchUpdate(nowReportPoList);
            reportPoList = nowReportPoList;
        }
        return reportPoList;
    }

    private Boolean bindingDoubleAccountToMatch() {
        List<WebDoubleIntegralRecord> webDoubleIntegralRecords = webDoubleIntegralRecordService.selectUnsettledRecordList();
        Map<Long,String> accMap = new HashMap<>();
        List<Long> updateList = new ArrayList<>();
        for (WebDoubleIntegralRecord record : webDoubleIntegralRecords) {
            Map<Long, Long> map = webMatchPlayerInfoDao.bindingDoubleAccountToMatch(record.getAccountId(), record.getCreatedTime());
            if(map != null && map.size() == 2){
                Long matchId = map.get("match_id");
                if (accMap.containsKey(matchId)) {
                    String accountId = accMap.get(matchId);
                    accountId = accountId + "," + map.get("account_id");
                    accMap.put(matchId, accountId);
                } else {
                    accMap.put(matchId, map.get("account_id")+"");
                }
            }else{
                //TODO  回滚点击双倍事件

            }
            updateList.add(record.getId());
        }
        return operationInfoToDBService.batchUpdateDoubleAccount(accMap, updateList);
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
            List<String> doubleAccountIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(webMatchDetailPo.getDoubleAccount())){
                doubleAccountIds = Arrays.asList(webMatchDetailPo.getDoubleAccount().split(","));
            }
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
            List<String> finalDoubleAccountIds = doubleAccountIds;
            Integer finalFraction2 = fraction;
            winPlayerID.forEach(item -> reportPoList.stream().filter(webUserDotaReportPo -> webUserDotaReportPo.getUserId().longValue() == item.longValue()).forEach(webUserDotaReportPo -> {
                int finalFraction = finalFraction2;
                Integer integral = webUserDotaReportPo.getIntegral();
                if(finalDoubleAccountIds.contains(item.toString())){
                    finalFraction = finalFraction *2;
                }
                int count = integral + finalFraction;
                webUserDotaReportPo.setIntegral(count);
                updateIntegralList.add(webUserDotaReportPo);
            }));
            Integer finalFraction3 = fraction;
            losePlayerID.forEach(item -> reportPoList.stream().filter(webUserDotaReportPo -> webUserDotaReportPo.getUserId().longValue() == item.longValue()).forEach(webUserDotaReportPo -> {
                int finalFraction1 = finalFraction3;
                Integer integral = webUserDotaReportPo.getIntegral();
                if(finalDoubleAccountIds.contains(item.toString())){
                    finalFraction1 = finalFraction1 *2;
                }
                int count = integral - finalFraction1;
                webUserDotaReportPo.setIntegral(count);
                updateIntegralList.add(webUserDotaReportPo);
            }));
            operationInfoToDBService.batchUpdateIntegralToDB(updateIntegralList, webMatchDetailPo);
        });
        return true;
    }
    @Override
    public List<WebUserDotaReport> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport) {
        return webUserDotaReportDao.selectWebUserDotaReportList(webUserDotaReport);
    }

    @Override
    public Map<String, List<Map<String, Double>>> statisticsTopInfoList() {
        Map<String, List<Map<String, Double>>> map = new HashMap<>(7);
        //最高积分 TOP3
        List<Map<String,Double>> highestPointsTopList = webUserDotaReportDao.getHighestPointsTop(3);
        map.put("highestPoints",highestPointsTopList);
        //最高胜率 TOP3
        List<Map<String,Double>> highestWinPerTopList = webUserDotaReportDao.getHighestWinPerTop(3);
        map.put("highestWinningPercentage",highestWinPerTopList);
        //最高KDA TOP3
        List<Map<String,Double>> highestKDATopList = webUserDotaReportDao.getHighestKDATop(3);
        map.put("highestKDA",highestKDATopList);
        //最长连胜 TOP3
        List<Map<String,Double>> longestWinningStreakTopList = webUserDotaReportDao.getLongestWinningStreakTop(3);
        map.put("longestWinningStreak",longestWinningStreakTopList);
        //最高场均击杀 TOP3
        List<Map<String,Double>> highestKillsPerGameTopList = webUserDotaReportDao.getHighestKillsPerGameTop(3);
        map.put("highestKillsPerGame",highestKillsPerGameTopList);
        //最少场均死亡 TOP3
        List<Map<String,Double>> leastDeathPerGameTopList = webUserDotaReportDao.getLeastDeathPerGameTop(3);
        map.put("leastDeathPerGame",leastDeathPerGameTopList);
        //最高场均助攻 TOP3
        List<Map<String,Double>> highestAssistsPerGameTopList = webUserDotaReportDao.getHighestAssistsPerGameTop(3);
        map.put("highestAssistsPerGame",highestAssistsPerGameTopList);
        return map;
    }

}
