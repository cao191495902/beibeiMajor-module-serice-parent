package com.beibeiMajor.web.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author lenovo
 */
@Data
public class HeroBean {

    @JSONField(name = "name")
    private String enName;
    @JSONField(name = "id")
    private Integer heroId;
    @JSONField(name = "localized_name")
    private String zhName;
    @JSONField(name = "icon")
    private String icon;
}
