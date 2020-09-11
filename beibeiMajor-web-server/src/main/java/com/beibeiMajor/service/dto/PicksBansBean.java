package com.beibeiMajor.service.dto;

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
    private boolean isPick;
    /**
     * 英雄ID
     */
    @JSONField(name = "hero_id")
    private int heroId;
    /**
     * 夜宴 or 天辉
     */
    @JSONField(name = "team")
    private int team;
    /**
     * 选择顺序
     */
    @JSONField(name = "order")
    private int order;
}
