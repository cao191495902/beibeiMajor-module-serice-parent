package com.beibeiMajor.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beibeiMajor.service.DotaGameInfoService;
import com.beibeiMajor.service.MatchDetailInfoService;
import com.beibeiMajor.service.dto.MatchDetailDto;
import com.beibeiMajor.service.dto.MatchDto;
import com.beibeiMajor.thread.GetMatchDetailThread;
import com.beibeiMajor.util.executor.LocalExecutorMananger;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
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

import static com.beibeiMajor.util.executor.LocalExecutorMananger.getExecutorService;

/**
 * @author lenovo
 */
@Service
@Slf4j
public class DotaGameInfoServiceImpl implements DotaGameInfoService {

    @Value("${customization.leagueMatchId}")
    private String leagueMatch;

    @Value("${customization.key}")
    private String key;

    @Resource
    private MatchDetailInfoService matchDetailInfoService;

    @Override
    public List<Long> getGameIdOfLeagueMatch() {
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

        //STEP 2  多线程查询比赛详细信息
        List<MatchDetailDto> list = new ArrayList<>();
        gameIdOfLeagueMatch.forEach(item ->{
            ExecutorService executorService = getExecutorService(LocalExecutorMananger.MATCH_MANAGE);
            Future<MatchDetailDto> future = executorService.submit(
                new GetMatchDetailThread(matchDetailInfoService, item)
            );
            try {
                list.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        //STEP 3  批量插入数据库

        return null;
    }

}
