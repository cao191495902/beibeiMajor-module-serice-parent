package com.beibeiMajor.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beibeiMajor.web.service.MatchDetailInfoService;
import com.beibeiMajor.web.service.dto.HeroBean;
import com.beibeiMajor.web.service.dto.MatchDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public List<HeroBean> GetHeroesDetailInfo() {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.steampowered.com/IEconDOTA2_570/GetHeroes/v1/?" + "key=" + key + "&language=zh_cn");
        ResponseHandler<String> handler = new BasicResponseHandler();
        String execute;
        try {
            execute = client.execute(httpGet, handler);
            Map<String,Object> json = (Map) JSONObject.parse(execute);
            if(json.size() > 0 && json.get("result") != null){
                JSONObject result = (JSONObject)json.get("result");
                JSONArray heroes = (JSONArray)result.get("heroes");
                List<HeroBean> list = JSONObject.parseArray(heroes.toJSONString(), HeroBean.class);
                return list;
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

}
