package com.beibeiMajor.web.controller;

import com.beibeiMajor.system.service.IWebUserService;
import com.beibeiMajor.web.service.DotaGameInfoService;
import com.beibeiMajor.web.service.ReportInfoService;
import org.springframework.cache.CacheManager;
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
    @Resource
    private CacheManager cacheManager;
    @Resource
    private IWebUserService webUserService;



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
     * 增量更新用户数据
     */
    @GetMapping("/synWebUser")
    public Object synWebUser() {
        webUserService.selectInsertWebUser();
       return "success";
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

    /**
     * 清除缓存
     */
    @GetMapping("/clearCache")
    public Object clearCache(String cacheName) {
        cacheManager.getCache(cacheName).clear();
        return "success";
    }


    /**
     * 手动添加比赛数据以后 重跑当前联赛report数据
     */
    @GetMapping("/runReport")
    public Object runReport() {
        //清除当前联赛的结算状态
        dotaGameInfoService.resetSettlementStatus();

        reportInfoService.deleteReport();

        reportInfoService.handlerMatchInfoToReport();

        //清除缓存
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear();
        }
        return "success";
    }
}
