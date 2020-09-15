package com.beibeiMajor.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 英雄信息表(WebDotaHero)实体类
 *
 * @author makejava
 * @since 2020-09-15 11:04:00
 */
@Data
public class WebDotaHeroPo implements Serializable {
    private static final long serialVersionUID = -40979901308728979L;
    /**
     * 英雄id
     */
    private Integer heroId;
    /**
     * 英雄英文名称
     */
    private String enName;
    /**
     * 英雄中文名称
     */
    private String zhName;

}