package com.beibeiMajor.web.service.dto;

import lombok.Data;

/**
 * 积分数据显示dto
 */
@Data
public class TopBean {

    //参战率
    private String warRate;
    //胜率
    private String winRate;
    //死的
    private String deathPerGame;
    //击杀
    private String killsPerGame;
    //助攻
    private String assistsPerGame;
    //kad
    private String kad;
    //英雄胜率
    private String heroWinRate;
    //场均英雄数量
    private String heroRate;
    //英雄次数
    private String heroCount;

}
