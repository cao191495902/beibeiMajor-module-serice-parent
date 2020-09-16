package com.beibeiMajor.web.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author zy
 */
@Data
public class PicksBansBean {

    /**
     * ban or pick
     */
    @JSONField(name = "is_pick")
    private Boolean isPick;
    /**
     * 英雄ID
     */
    @JSONField(name = "hero_id")
    private Long heroId;
    /**
     * 选择顺序
     */
    @JSONField(name = "order")
    private int order;
    /**
     * 夜宴 or 天辉
     */
    @JSONField(name = "team")
    private Boolean team;
}
