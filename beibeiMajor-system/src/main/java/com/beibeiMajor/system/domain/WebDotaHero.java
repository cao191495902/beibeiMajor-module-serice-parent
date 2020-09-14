package com.beibeiMajor.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

/**
 * 英雄信息对象 web_dota_hero
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public class WebDotaHero extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 英雄id */
    private Long heroId;

    /** 英雄英文名称 */
    @Excel(name = "英雄英文名称")
    private String enName;

    /** 英雄中文名称 */
    @Excel(name = "英雄中文名称")
    private String zhName;

    public void setHeroId(Long heroId) 
    {
        this.heroId = heroId;
    }

    public Long getHeroId() 
    {
        return heroId;
    }
    public void setEnName(String enName) 
    {
        this.enName = enName;
    }

    public String getEnName() 
    {
        return enName;
    }
    public void setZhName(String zhName) 
    {
        this.zhName = zhName;
    }

    public String getZhName() 
    {
        return zhName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("heroId", getHeroId())
            .append("enName", getEnName())
            .append("zhName", getZhName())
            .toString();
    }
}
