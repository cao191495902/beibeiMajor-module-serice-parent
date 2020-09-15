package com.beibeiMajor.task;

import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.service.DotaGameInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("dotaTask")
public class DotaTask {

    @Resource
    private DotaGameInfoService dotaGameInfoService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void getGameInfo(){
        //每日定时更新比赛信息
        List<Long> gameIdOfLeagueMatch = dotaGameInfoService.getGameIdOfLeagueMatch();
        dotaGameInfoService.insertMatchDetailInfo(gameIdOfLeagueMatch);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
}
