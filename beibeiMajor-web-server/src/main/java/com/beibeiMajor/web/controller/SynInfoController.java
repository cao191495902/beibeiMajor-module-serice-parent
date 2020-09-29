package com.beibeiMajor.web.controller;

import com.beibeiMajor.web.service.DotaGameInfoService;
import com.beibeiMajor.web.service.ReportInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模态窗口
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/syn")
public class SynInfoController {

    @Resource
    private DotaGameInfoService dotaGameInfoService;
    @Resource
    private ReportInfoService reportInfoService;

    /**
     * 增量更新比赛数据
     */
    @GetMapping("/synGameIdOfLeagueMatch")
    public Object incrementalSynGameIdOfLeagueMatch() {
        List<Long> gameIdOfLeagueMatch = dotaGameInfoService.getGameIdOfLeagueMatch();
        if (dotaGameInfoService.insertMatchDetailInfo(gameIdOfLeagueMatch)) {
            return "success";
        } else return "failed";
    }

    /**
     * 全量更新英雄数据
     */
    @GetMapping("/synHeroesInfo")
    public Object synHeroesInfo() {
        dotaGameInfoService.insertHeroesInfo();
        return "success";
    }

    /**
     * 增量更新report数据
     */
    @GetMapping("/synReportInfo")
    public Object synReportInfo() {
        reportInfoService.handlerMatchInfoToReport();
        return "success";
    }

}
