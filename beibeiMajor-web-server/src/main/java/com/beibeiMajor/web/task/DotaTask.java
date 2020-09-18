package com.beibeiMajor.web.task;

import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.service.DotaGameInfoService;
import com.beibeiMajor.web.service.ReportInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@EnableScheduling
@Component("dotaTask")
@Slf4j
public class DotaTask {

    @Resource
    private DotaGameInfoService dotaGameInfoService;
    @Resource
    private ReportInfoService reportInfoService;
    @Resource
    private IWebUserService webUserService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    @Scheduled(cron = "0 0 2 * * ?") // 每天2点触发
    public void getGameInfo(){
        log.info("Schedule getGameInfo start");
        //每日定时更新比赛信息
        List<Long> gameIdOfLeagueMatch = dotaGameInfoService.getGameIdOfLeagueMatch();
        dotaGameInfoService.insertMatchDetailInfo(gameIdOfLeagueMatch);
        log.info("Schedule getGameInfo end");
    }
    @Scheduled(cron = "0 15 2 * * ?")  // 每天2点15触发
    public void updateUserInfo(){
        log.info("Schedule updateUserInfo start");
        //每日更新比赛信息之后，展示数据之前更新用户信息
        webUserService.selectInsertWebUser();
        log.info("Schedule updateUserInfo end");
    }
    @Scheduled(cron = "0 30 2 * * ?") // 每天2点15触发
    public void handlerGameInfoToShow(){
        log.info("Schedule handlerGameInfoToShow start");
        //每日定时更新比赛信息后计算后展示数据
        reportInfoService.handlerMatchInfoToReport();
        log.info("Schedule handlerGameInfoToShow end");
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
}
