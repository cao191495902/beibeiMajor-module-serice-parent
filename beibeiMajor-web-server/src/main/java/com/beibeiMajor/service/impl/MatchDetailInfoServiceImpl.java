package com.beibeiMajor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beibeiMajor.mapper.dao.WebAbilityUpgradesDao;
import com.beibeiMajor.mapper.dao.WebMatchDetailDao;
import com.beibeiMajor.mapper.dao.WebMatchPicksDao;
import com.beibeiMajor.mapper.dao.WebMatchPlayerInfoDao;
import com.beibeiMajor.mapper.po.WebAbilityUpgradesPo;
import com.beibeiMajor.mapper.po.WebMatchDetailPo;
import com.beibeiMajor.mapper.po.WebMatchPicksPo;
import com.beibeiMajor.mapper.po.WebMatchPlayerPo;
import com.beibeiMajor.service.MatchDetailInfoService;
import com.beibeiMajor.service.dto.MatchDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MatchDetailInfoServiceImpl implements MatchDetailInfoService {

    @Value("${customization.leagueMatchId}")
    private String leagueMatch;

    @Value("${customization.key}")
    private String key;

    @Resource
    private WebAbilityUpgradesDao webAbilityUpgradesDao;
    @Resource
    private WebMatchDetailDao webMatchDetailDao;
    @Resource
    private WebMatchPicksDao webMatchPicksDao;
    @Resource
    private WebMatchPlayerInfoDao webMatchPlayerInfoDao;

    @Override
    public MatchDetailDto getMatchDetailById(Long matchId) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/v1?match_id=" + matchId + "&key=" + key);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String execute;
        try {
            execute = client.execute(httpGet, handler);
            Map<String,Object> json = (Map) JSONObject.parse(execute);
            if(json.size() > 0 && json.get("result") != null){
                JSONObject result = (JSONObject)json.get("result");
                MatchDetailDto matchDetail = JSON.toJavaObject(result, MatchDetailDto.class);
                return matchDetail;
            }
        } catch (IOException e){
            log.error(e.getMessage(), "get dota match detail info failed");
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchIntoDB(List<WebMatchDetailPo> detailList, List<WebMatchPicksPo> picksList, List<WebMatchPlayerPo> playerList, List<WebAbilityUpgradesPo> abilityUpgradesList) {
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


}