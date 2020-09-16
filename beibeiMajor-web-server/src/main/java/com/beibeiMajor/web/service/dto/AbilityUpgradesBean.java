package com.beibeiMajor.web.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lenovo
 */
@Data
public class AbilityUpgradesBean {
    /**
     * 玩家选择技能ID
     */
    private Long ability;
    /**
     * 玩家加点时间
     */
    private Long time;
    /**
     * 玩家等级
     */
    private int level;
}
