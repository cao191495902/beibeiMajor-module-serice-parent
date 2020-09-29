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
import com.beibeiMajor.web.service.dto.MyMatchDetailBean;
import com.beibeiMajor.web.service.dto.TopBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
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
            //TODO 优化循环SQL
            Map<Long, Long> map = webMatchPlayerInfoDao.bindingDoubleAccountToMatch(record.getAccountId(), record.getCreatedTime());
            if(map != null && map.size() == 2){
                Long matchId = map.get("match_id");
                if (accMap.containsKey(matchId)) {
                    String accountId = accMap.get(matchId);
                    accountId = accountId + "," + map.get("account_id");
                    accMap.put(matchId, accountId);
                } else {
                    accMap.put(matchId, map.get("account_id").toString());
                }
            }else{
                operationInfoToDBService.rollbackDoubleIntegralRecord(record.getAccountId());
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
            List<WebUserDotaReportPo> newReportPoList = new ArrayList<>();
            webUsers.forEach(webUser -> {
                WebUserDotaReportPo reportPo = new WebUserDotaReportPo();
                reportPo.setUserId(webUser.getAccountId());
                reportPo.setIntegral(3000);
                reportPoList.add(reportPo);
                newReportPoList.add(reportPo);
            });
            if (newReportPoList.size() > 0) {
                try {
                    webUserDotaReportDao.batchInsert(newReportPoList);
                } catch (Exception e) {
                    log.error("batch insert new report info failed", e.getMessage());
                }
            }

            return reportPoList;
        }
        return reportPoList;
    }

    public Boolean handlerIntegralOfUsers(){
        //step 1 按照每把比赛进行计算
        List<Long> updateMatchList = new ArrayList<>();
        List<MatchPlayerIntegralPo> matchPlayerIntegralPoList = new ArrayList<>();
        List<WebUserDotaReportPo> reportPoList = webUserDotaReportDao.queryAll(new WebUserDotaReportPo());
        List<PlayerWinOrLosePo> winOrLosePlayerList = webMatchPlayerInfoDao.getPlayerWinOrLoseList();
        winOrLosePlayerList.forEach(PlayerWinOrLosePo -> {
            List<String> doubleAccountIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(PlayerWinOrLosePo.getDoubleAccount())){
                doubleAccountIds = Arrays.asList(PlayerWinOrLosePo.getDoubleAccount().split(","));
            }
            List<String> winPlayerList = Arrays.asList(PlayerWinOrLosePo.getWinArray().split(","));
            List<String> losePlayerList = Arrays.asList(PlayerWinOrLosePo.getLoseArray().split(","));
            List<Integer> winList = new ArrayList<>();
            List<Integer> loseList = new ArrayList<>();
            winPlayerList.forEach(winPlayerId -> reportPoList.forEach(reportPo->{
                if ((reportPo.getUserId().toString()).equals(winPlayerId)){
                    winList.add(reportPo.getIntegral());
                }
            }));
            int winSum = winList.stream().reduce(Integer::sum).orElse(0);
            losePlayerList.forEach(losePlayerId -> reportPoList.forEach(reportPo->{
                if ((reportPo.getUserId().toString()).equals(losePlayerId)){
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
            winPlayerList.forEach(item -> reportPoList.stream().filter(webUserDotaReportPo -> webUserDotaReportPo.getUserId().toString().equals(item)).forEach(webUserDotaReportPo -> {
                MatchPlayerIntegralPo matchPlayerIntegralPo = new MatchPlayerIntegralPo();
                matchPlayerIntegralPo.setAccountId(Long.valueOf(item));
                matchPlayerIntegralPo.setMatchId(PlayerWinOrLosePo.getMatchId());
                matchPlayerIntegralPo.setBeforeIntegral(webUserDotaReportPo.getIntegral());
                int finalFraction = finalFraction2;
                Integer integral = webUserDotaReportPo.getIntegral();
                if(finalDoubleAccountIds.contains(item)){
                    finalFraction = finalFraction *2;
                }
                int count = integral + finalFraction;
                webUserDotaReportPo.setIntegral(count);
                matchPlayerIntegralPo.setAfterIntegral(count);
                matchPlayerIntegralPoList.add(matchPlayerIntegralPo);
            }));
            Integer finalFraction3 = fraction;
            losePlayerList.forEach(item -> reportPoList.stream().filter(webUserDotaReportPo -> webUserDotaReportPo.getUserId().toString().equals(item)).forEach(webUserDotaReportPo -> {
                MatchPlayerIntegralPo matchPlayerIntegralPo = new MatchPlayerIntegralPo();
                matchPlayerIntegralPo.setAccountId(Long.valueOf(item));
                matchPlayerIntegralPo.setMatchId(PlayerWinOrLosePo.getMatchId());
                matchPlayerIntegralPo.setBeforeIntegral(webUserDotaReportPo.getIntegral());
                int finalFraction1 = finalFraction3;
                Integer integral = webUserDotaReportPo.getIntegral();
                if(finalDoubleAccountIds.contains(item)){
                    finalFraction1 = finalFraction1 *2;
                }
                int count = integral - finalFraction1;
                webUserDotaReportPo.setIntegral(count);
                matchPlayerIntegralPo.setAfterIntegral(count);
                matchPlayerIntegralPoList.add(matchPlayerIntegralPo);
            }));
        });
        winOrLosePlayerList.forEach(playerWinOrLosePo -> updateMatchList.add(playerWinOrLosePo.getMatchId()));
        operationInfoToDBService.batchUpdateIntegralToDB(reportPoList, matchPlayerIntegralPoList, updateMatchList);
        return true;
    }
    @Override
    @Cacheable(value = "selectWebUserDotaReportList",key="#root.methodName+'_'+#webUserDotaReport.userId+'_'+#pageNum+'_'+#pageSize")
    public List<WebUserDotaReport> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport,Integer pageNum,Integer pageSize) {
        return webUserDotaReportDao.selectWebUserDotaReportList(webUserDotaReport);
    }

    @Override
    @Cacheable(value = "statisticsTopInfoList",key="#root.methodName")
    public List<TopBean> statisticsTopInfoList() {
        List<TopBean> result = new ArrayList<>(3);
        TopBean top1 = new TopBean();
        TopBean top2 = new TopBean();
        TopBean top3 = new TopBean();

        //最高积分 TOP3
        List<String> highestPointsTopList = webUserDotaReportDao.getHighestPointsTop(3);
        if (highestPointsTopList != null && highestPointsTopList.size() >= 1)
            top1.setIntegral(highestPointsTopList.get(0));
        if (highestPointsTopList != null && highestPointsTopList.size() >= 2)
            top2.setIntegral(highestPointsTopList.get(1));
        if (highestPointsTopList != null && highestPointsTopList.size() >= 3)
            top3.setIntegral(highestPointsTopList.get(2));
        //最高胜率 TOP3
        List<String> highestWinPerTopList = webUserDotaReportDao.getHighestWinPerTop(3);
        if (highestWinPerTopList != null && highestWinPerTopList.size() >= 1)
            top1.setWinRate(highestWinPerTopList.get(0));
        if (highestWinPerTopList != null && highestWinPerTopList.size() >= 2)
            top2.setWinRate(highestWinPerTopList.get(1));
        if (highestWinPerTopList != null && highestWinPerTopList.size() >= 3)
            top3.setWinRate(highestWinPerTopList.get(2));
        //最高KDA TOP3
        List<String> highestKDATopList = webUserDotaReportDao.getHighestKDATop(3);
        if (highestKDATopList != null && highestKDATopList.size() >= 1)
            top1.setKad(highestKDATopList.get(0));
        if (highestKDATopList != null && highestKDATopList.size() >= 2)
            top2.setKad(highestKDATopList.get(1));
        if (highestKDATopList != null && highestKDATopList.size() >= 3)
            top3.setKad(highestKDATopList.get(2));
        //最长连胜 TOP3
        List<String> longestWinningStreakTopList = webUserDotaReportDao.getLongestWinningStreakTop(3);
        if (longestWinningStreakTopList != null && longestWinningStreakTopList.size() >= 1)
            top1.setWinningStreak(longestWinningStreakTopList.get(0));
        if (longestWinningStreakTopList != null && longestWinningStreakTopList.size() >= 2)
            top2.setWinningStreak(longestWinningStreakTopList.get(1));
        if (longestWinningStreakTopList != null && longestWinningStreakTopList.size() >= 3)
            top3.setWinningStreak(longestWinningStreakTopList.get(2));
        //最高场均击杀 TOP3
        List<String> highestKillsPerGameTopList = webUserDotaReportDao.getHighestKillsPerGameTop(3);
        if (highestKillsPerGameTopList != null && highestKillsPerGameTopList.size() >= 1)
            top1.setKillsPerGame(highestKillsPerGameTopList.get(0));
        if (highestKillsPerGameTopList != null && highestKillsPerGameTopList.size() >= 2)
            top2.setKillsPerGame(highestKillsPerGameTopList.get(1));
        if (highestKillsPerGameTopList != null && highestKillsPerGameTopList.size() >= 3)
            top3.setKillsPerGame(highestKillsPerGameTopList.get(2));
        //最少场均死亡 TOP3
        List<String> leastDeathPerGameTopList = webUserDotaReportDao.getLeastDeathPerGameTop(3);
        if (leastDeathPerGameTopList != null && leastDeathPerGameTopList.size() >= 1)
            top1.setDeathPerGame(leastDeathPerGameTopList.get(0));
        if (leastDeathPerGameTopList != null && leastDeathPerGameTopList.size() >= 2)
            top2.setDeathPerGame(leastDeathPerGameTopList.get(1));
        if (leastDeathPerGameTopList != null && leastDeathPerGameTopList.size() >= 3)
            top3.setDeathPerGame(leastDeathPerGameTopList.get(2));
        //最高场均助攻 TOP3
        List<String> highestAssistsPerGameTopList = webUserDotaReportDao.getHighestAssistsPerGameTop(3);
        if (highestAssistsPerGameTopList != null && highestAssistsPerGameTopList.size() >= 1)
            top1.setAssistsPerGame(highestAssistsPerGameTopList.get(0));
        if (highestAssistsPerGameTopList != null && highestAssistsPerGameTopList.size() >= 2)
            top2.setAssistsPerGame(highestAssistsPerGameTopList.get(1));
        if (highestAssistsPerGameTopList != null && highestAssistsPerGameTopList.size() >= 3)
            top3.setAssistsPerGame(highestAssistsPerGameTopList.get(2));
        result.add(top1);
        result.add(top2);
        result.add(top3);
        return result;
    }

    @Override
    public List<WebUser> getYesTodayTopUser(Long startDate,Long endDate) {
        return webUserDotaReportDao.getYesTodayTopUser(startDate,endDate);
    }

    @Override
    @Cacheable(value = "getMyRecordList", key = "#root.methodName+'_'+#userId+'_'+#pageNum+'_'+#pageSize")
    public List<MyMatchDetailBean> getMyRecordList(String userId,Integer pageNum,Integer pageSize) {
        return webUserDotaReportDao.getMyRecordList(userId);
    }

}
