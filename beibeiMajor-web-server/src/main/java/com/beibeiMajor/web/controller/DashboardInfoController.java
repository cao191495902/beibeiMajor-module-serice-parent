package com.beibeiMajor.web.controller;

import com.beibeiMajor.service.DotaGameInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模态窗口
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardInfoController {

    @Resource
    private DotaGameInfoService dotaGameInfoService;

    /**
     * 模态窗口
     */
    @GetMapping("/getGameIdOfLeagueMatch")
    public void dialog() {
        List<Long> gameIdOfLeagueMatch = dotaGameInfoService.getGameIdOfLeagueMatch();
        dotaGameInfoService.insertMatchDetailInfo(gameIdOfLeagueMatch);
    }
}
