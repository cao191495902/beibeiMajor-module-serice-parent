package com.beibeiMajor.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beibeiMajor.system.domain.WebLeague;
import com.beibeiMajor.system.service.IWebMatchDetailService;
import com.beibeiMajor.web.mapper.dao.WebMatchDetailDao;
import com.beibeiMajor.web.mapper.po.*;
import com.beibeiMajor.web.service.DotaGameInfoService;
import com.beibeiMajor.web.service.MatchDetailInfoService;
import com.beibeiMajor.web.service.OperationInfoToDBService;
import com.beibeiMajor.web.service.dto.*;
import com.beibeiMajor.web.thread.GetMatchDetailThread;
import com.beibeiMajor.web.util.executor.LocalExecutorMananger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.beibeiMajor.web.util.executor.LocalExecutorMananger.getExecutorService;

/**
 * @author lenovo
 */
@Service
@Slf4j
public class DotaGameInfoServiceImpl implements DotaGameInfoService {

//    @Value("${customization.leagueMatchId}")
//    private String leagueMatch;
//
//    @Value("${customization.key}")
//    private String key;

    @Resource
    private MatchDetailInfoService matchDetailInfoService;
    @Resource
    private WebMatchDetailDao webMatchDetailDao;
    @Resource
    private OperationInfoToDBService operationInfoToDBService;
    @Resource
    IWebMatchDetailService webMatchDetailService;

    @Override
    public List<Long> getGameIdOfLeagueMatch() {

        WebLeague webLeague = webMatchDetailService.getDefaultLeagueInfo();
        String key = webLeague.getKey();
        Integer leagueMatch = webLeague.getId();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/v1?league_id=" + leagueMatch + "&key=" + key);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String execute;
        try {
            execute = client.execute(httpGet, handler);
            Map<String,Object> json = (Map) JSONObject.parse(execute);
            if(json.size() > 0 && json.get("result") != null){
                JSONObject result = (JSONObject)json.get("result");
                JSONArray matches = (JSONArray)result.get("matches");
                List<MatchDto> list = JSONObject.parseArray(matches.toJSONString(), MatchDto.class);
                List<Long> collect = list.stream().map(MatchDto::getMatchId).collect(Collectors.toList());
                return collect;
            }
        } catch (IOException e){
            log.error(e.getMessage(), "get dota matches info failed");
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                log.error("client close failed!",e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Boolean insertMatchDetailInfo(List<Long> gameIdOfLeagueMatch) {
        //STEP 1  查询数据库现有比赛ID 与联赛ID做差集  只做增量更新
        List<Long> oldMatchId = webMatchDetailDao.queryAllId();
        gameIdOfLeagueMatch.removeAll(oldMatchId);
        //STEP 2  多线程查询比赛详细信息
        if (CollectionUtils.isNotEmpty(gameIdOfLeagueMatch)){
            List<MatchDetailDto> list = new ArrayList<>();
            gameIdOfLeagueMatch.forEach(item ->{
                ExecutorService executorService = getExecutorService(LocalExecutorMananger.MATCH_MANAGE);
                Future<MatchDetailDto> future = executorService.submit(
                        new GetMatchDetailThread(matchDetailInfoService, item)
                );
                try {
                    MatchDetailDto matchDetailDto = future.get();
                    if (matchDetailDto != null)
                        list.add(matchDetailDto);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            //STEP 3  批量插入数据库
            return MatchDetailInfoToDB(list);
        }
        return true;
    }

    @Override
    public Boolean insertHeroesInfo() {
        List<HeroBean> heroBeans = matchDetailInfoService.GetHeroesDetailInfo();
        return HeroesDetailInfoToDB(heroBeans);
    }

    @Override
    public void updateUserInfo() {

    }

    private Boolean HeroesDetailInfoToDB(List<HeroBean> heroBeans) {
        List<WebDotaHeroPo> lists = new ArrayList<>();
        heroBeans.forEach(heroBean -> {
            WebDotaHeroPo heroPo = new WebDotaHeroPo();
            BeanUtils.copyProperties(heroBean, heroPo);
            lists.add(heroPo);
        });
        return operationInfoToDBService.HeroesBatchIntoDB(lists) ;
    }

    public Boolean MatchDetailInfoToDB(List<MatchDetailDto> list){
        List<WebMatchPlayerPo> playerList = new ArrayList<>();
        List<WebMatchDetailPo> detailList = new ArrayList<>();
        List<WebMatchPicksPo> picksList = new ArrayList<>();
        List<WebAbilityUpgradesPo> abilityUpgradesList = new ArrayList<>();
        if (list != null && CollectionUtils.isNotEmpty(list)){
            list.forEach(item->{
                List<PlayersBean> players = item.getPlayers();
                if (players != null && CollectionUtils.isNotEmpty(players)){
                    players.forEach(player -> {
                        List<AbilityUpgradesBean> abilityUpgrades = player.getAbilityUpgrades();
                        if (abilityUpgrades != null && CollectionUtils.isNotEmpty(abilityUpgrades)){
                            abilityUpgrades.forEach(abilityUpgradesBean -> {
                                WebAbilityUpgradesPo abilityUpgradesPo = new WebAbilityUpgradesPo();
                                BeanUtils.copyProperties(abilityUpgradesBean, abilityUpgradesPo);
                                abilityUpgradesPo.setMatchId(item.getMatchId());
                                abilityUpgradesPo.setAccountId(player.getAccountId());
                                abilityUpgradesPo.setAbilityId(abilityUpgradesBean.getAbility());
                                abilityUpgradesList.add(abilityUpgradesPo);
                            });
                        }
                        WebMatchPlayerPo info = new WebMatchPlayerPo();
                        BeanUtils.copyProperties(player, info);
                        info.setMatchId(item.getMatchId());
                        playerList.add(info);
                    });
                }
                List<PicksBansBean> picksBans = item.getPicks_bans();
                if (picksBans != null && CollectionUtils.isNotEmpty(picksBans)){
                    picksBans.forEach(picksBansBean -> {
                        WebMatchPicksPo picksPo = new WebMatchPicksPo();
                        BeanUtils.copyProperties(picksBansBean, picksPo);
                        picksPo.setMatchId(item.getMatchId());
                        picksList.add(picksPo);
                    });
                }
                WebMatchDetailPo detailPo = new WebMatchDetailPo();
                BeanUtils.copyProperties(item, detailPo);
                detailList.add(detailPo);
            });
             return operationInfoToDBService.MatchsBatchIntoDB(detailList, picksList, playerList, abilityUpgradesList);
        }
        return true;
    }

}
