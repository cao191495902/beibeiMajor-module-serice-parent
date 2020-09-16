package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * (WebMatchPicks)实体类
 *
 * @author makejava
 * @since 2020-09-14 11:15:41
 */
@Data
public class WebMatchPicksPo implements Serializable {
    private static final long serialVersionUID = 596002130565938656L;

    private Long id;
    /**
     * 比赛Id
     */
    private Long matchId;
    /**
     * B/P
     */
    private Boolean isPick;
    /**
     * 英雄Id
     */
    private Long heroId;
    /**
     * 阵营
     */
    private Boolean team;
    /**
     * 选择顺序
     */
    private Integer order;

}