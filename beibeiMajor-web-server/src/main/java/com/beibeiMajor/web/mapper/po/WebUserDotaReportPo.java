package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分表(WebUserDotaReport)实体类
 *
 * @author makejava
 * @since 2020-09-15 15:44:13
 */
@Data
public class WebUserDotaReportPo implements Serializable {
    private static final long serialVersionUID = -63024680516900733L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 积分
     */
    private Integer integral;
    /**
     * 胜率
     */
    private Double winRate;
    /**
     * mvp次数
     */
    private Integer mvpCount;
    /**
     * 场均KDA
     */
    private Double kda;
    /**
     * 当前连胜/连败次数
     */
    private Integer curMaxCount;
    /**
     * 是否是连胜
     */
    private Boolean isWin;
    /**
     * 最后比赛时间
     */
    private Date lastPlayTime;
    /**
     * 场均击杀
     */
    private Double averageKills;
    /**
     * 场均阵亡
     */
    private Double averageDeaths;
    /**
     * 场均助攻
     */
    private Double averageAssists;
    /**
     * GPM
     */
    private Integer goldPerMin;
    /**
     * XPM
     */
    private Integer xpPerMin;

    private Integer rank;
    /**
     * 总比赛数
     */
    private Integer totalMatchesNum;

}