package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.system.domain.*;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.system.service.IWebMatchDetailService;
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
    @Resource
    IWebMatchDetailService webMatchDetailService;


    private static final int TOP_COUNT = 5;

    @Override
    public List<WebUserDotaReportPo> handlerMatchInfoToReport() {

        WebLeague webLeague = webMatchDetailService.getDefaultLeagueInfo();
        Integer leagueId = webLeague.getId();
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

                operationInfoToDBService.rollbackDoubleIntegralRecord(record.getAccountId(),1L,0,"系统回退",true);
            }
            updateList.add(record.getId());
        }
        return operationInfoToDBService.batchUpdateDoubleAccount(accMap, updateList);
    }

    private List<WebUserDotaReportPo> updateDotaReportInfo() {
        List<WebUserDotaReportPo> reportPoList = webUserDotaReportDao.queryAll(new WebUserDotaReportPo());
        List<WebUser> webUsers = webUserService.selectWebUserListByLeague();
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
            //2022年3月8日修改基数为15
            if (winSum > loseSum){
                int disparity = (winSum - loseSum) / 1000;
                fraction = 100 - disparity*15;
            }else{
                int disparity = (loseSum - winSum) / 1000;
                fraction = 100 + disparity*15;
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
    @Cacheable(value = "selectWebUserDotaReportList",key="#root.methodName+'_'+#webUserDotaReport.userId+'_'+#webUserDotaReport.leagueId+'_'+#pageNum+'_'+#pageSize")
    public List<WebUserDotaReportPo> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport,Integer pageNum,Integer pageSize) {
        return webUserDotaReportDao.selectWebUserDotaReportList(webUserDotaReport);
    }

    @Override
    @Cacheable(value = "statisticsTopInfoList",key="#root.methodName+'_'+#leagueId")
    public List<TopBean> statisticsTopInfoList(Integer leagueId) {

        List<TopBean> result = new ArrayList<>(TOP_COUNT);

        //参战率 TOP3
        List<String> warRateTopList = webUserDotaReportDao.getWarRateTop(TOP_COUNT, leagueId, "DESC");
        //最高胜率 TOP3
        List<String> highestWinPerTopList = webUserDotaReportDao.getHighestWinPerTop(TOP_COUNT, leagueId, "DESC");
        //最高KDA TOP3
        List<String> highestKDATopList = webUserDotaReportDao.getHighestKDATop(TOP_COUNT, leagueId, "DESC");
        //最高场均击杀 TOP3
        List<String> highestKillsPerGameTopList = webUserDotaReportDao.getHighestKillsPerGameTop(TOP_COUNT, leagueId, "DESC");
        //最少场均死亡 TOP3
        List<String> leastDeathPerGameTopList = webUserDotaReportDao.getLeastDeathPerGameTop(TOP_COUNT, leagueId, "ASC");
        //最高场均助攻 TOP3
        List<String> highestAssistsPerGameTopList = webUserDotaReportDao.getHighestAssistsPerGameTop(TOP_COUNT, leagueId, "DESC");
        //英雄胜率
        List<String> heroWinRateList = webUserDotaReportDao.getheroWinRateTop(TOP_COUNT, leagueId, "DESC");
        //英雄数量
        List<String> heroCountList = webUserDotaReportDao.getHeroCountTop(TOP_COUNT,leagueId, "DESC");
        //场均英雄次数占比
        List<String> heroRateList = webUserDotaReportDao.heroRateTop(TOP_COUNT, leagueId, "DESC");

        for (int i = 0; i < TOP_COUNT; i++) {
            TopBean top = new TopBean();

            if (warRateTopList != null && warRateTopList.size() > i) {
                top.setWarRate(warRateTopList.get(i));
            }
            if (highestWinPerTopList != null && highestWinPerTopList.size() > i) {
                top.setWinRate(highestWinPerTopList.get(i));
            }
            if (highestKDATopList != null && highestKDATopList.size() > i) {
                top.setKad(highestKDATopList.get(i));
            }
            if (highestKillsPerGameTopList != null && highestKillsPerGameTopList.size() > i) {
                top.setKillsPerGame(highestKillsPerGameTopList.get(i));
            }
            if (leastDeathPerGameTopList != null && leastDeathPerGameTopList.size() > i) {
                top.setDeathPerGame(leastDeathPerGameTopList.get(i));
            }
            if (highestAssistsPerGameTopList != null && highestAssistsPerGameTopList.size() > i) {
                top.setAssistsPerGame(highestAssistsPerGameTopList.get(i));
            }
            if (heroWinRateList != null && heroWinRateList.size() > i) {
                top.setHeroWinRate(heroWinRateList.get(i));
            }
            if (heroCountList != null && heroCountList.size() > i) {
                top.setHeroCount(heroCountList.get(i));
            }
            if (heroRateList != null && heroRateList.size() > i) {
                top.setHeroRate(heroRateList.get(i));
            }

            result.add(top);
        }
        return result;
    }

    @Override
    public List<WebUser> getYesTodayTopUser(Long startDate,Long endDate) {
        return webUserDotaReportDao.getYesTodayTopUser(startDate,endDate);
    }

    @Override
    @Cacheable(value = "getMyRecordList", key = "#root.methodName+'_'+#userId+'_'+#leagueId+'_'+#pageNum+'_'+#pageSize")
    public List<MyMatchDetailBean> getMyRecordList(String userId, Integer leagueId, Integer pageNum, Integer pageSize) {
        return webUserDotaReportDao.getMyRecordList(userId,leagueId);
    }


    @Override
    @Cacheable(value = "statisticsLossInfoList",key="#root.methodName+'_'+#leagueId")
    public List<TopBean> statisticsLossInfoList(Integer leagueId) {
        List<TopBean> result = new ArrayList<>(TOP_COUNT);

        //参战率 TOP3
        List<String> warRateTopList = webUserDotaReportDao.getWarRateTop(TOP_COUNT,leagueId, "ASC");
        //最高胜率 TOP3
        List<String> highestWinPerTopList = webUserDotaReportDao.getHighestWinPerTop(TOP_COUNT,leagueId, "ASC");
        //最高KDA TOP3
        List<String> highestKDATopList = webUserDotaReportDao.getHighestKDATop(TOP_COUNT,leagueId, "ASC");
        //最高场均击杀 TOP3
        List<String> highestKillsPerGameTopList = webUserDotaReportDao.getHighestKillsPerGameTop(TOP_COUNT,leagueId, "ASC");
        //最少场均死亡 TOP3
        List<String> leastDeathPerGameTopList = webUserDotaReportDao.getLeastDeathPerGameTop(TOP_COUNT,leagueId, "DESC");
        //最高场均助攻 TOP3
        List<String> highestAssistsPerGameTopList = webUserDotaReportDao.getHighestAssistsPerGameTop(TOP_COUNT,leagueId, "ASC");
        //英雄胜率
        List<String> heroWinRateList = webUserDotaReportDao.getheroWinRateTop(TOP_COUNT,leagueId, "ASC");

        //场均英雄次数占比
        List<String> heroRateList = webUserDotaReportDao.heroRateTop(TOP_COUNT,leagueId, "ASC");

        for (int i = 0; i < TOP_COUNT; i++) {
            TopBean top = new TopBean();

            if (warRateTopList != null && warRateTopList.size() > i) {
                top.setWarRate(warRateTopList.get(i));
            }
            if (highestWinPerTopList != null && highestWinPerTopList.size() > i) {
                top.setWinRate(highestWinPerTopList.get(i));
            }
            if (highestKDATopList != null && highestKDATopList.size() > i) {
                top.setKad(highestKDATopList.get(i));
            }
            if (highestKillsPerGameTopList != null && highestKillsPerGameTopList.size() > i) {
                top.setKillsPerGame(highestKillsPerGameTopList.get(i));
            }
            if (leastDeathPerGameTopList != null && leastDeathPerGameTopList.size() > i) {
                top.setDeathPerGame(leastDeathPerGameTopList.get(i));
            }
            if (highestAssistsPerGameTopList != null && highestAssistsPerGameTopList.size() > i) {
                top.setAssistsPerGame(highestAssistsPerGameTopList.get(i));
            }
            if (heroWinRateList != null && heroWinRateList.size() > i) {
                top.setHeroWinRate(heroWinRateList.get(i));
            }
            if (heroRateList != null && heroRateList.size() > i) {
                top.setHeroRate(heroRateList.get(i));
            }
            result.add(top);
        }
        return result;
    }

    @Override
    @Cacheable(value = "getRecentPerformList", key = "#root.methodName+'_'+#accountId+'_'+#leagueId")
    public List<RecentPerform> getRecentPerformList(Long accountId, Integer leagueId) {
        //先通过account和leagueId查询，如果场次少于5，则不计算
        List<MyMatchDetailBean> list = webUserDotaReportDao.getMyRecordList(accountId + "", leagueId);
        if (list == null || list.size() < 5) {
            return null;
        }
        List<RecentPerform> recentPerformList = new ArrayList<>(5);
        //获取五边形所需要的数据，近5场比赛的参战能力,KAD,打钱能力，输出能力，推塔能力

        //获取参战能力
        RecentPerform RecentPerformWar = webUserDotaReportDao.getMyWarRate(accountId,leagueId);
        recentPerformList.add(RecentPerformWar);

        //获取kad
        RecentPerform RecentPerformKad = webUserDotaReportDao.getMyKad(accountId,leagueId);
        recentPerformList.add(RecentPerformKad);

        //获取打钱能力
        RecentPerform RecentPerformGold = webUserDotaReportDao.getMyGold(accountId,leagueId);
        recentPerformList.add(RecentPerformGold);

        //获取输出能力
        RecentPerform RecentPerformDamage = webUserDotaReportDao.getMyDamage(accountId,leagueId);
        recentPerformList.add(RecentPerformDamage);

        //获取推塔能力
        RecentPerform RecentPerformTower = webUserDotaReportDao.getMyTower(accountId,leagueId);
        recentPerformList.add(RecentPerformTower);


        return recentPerformList;
    }
}
