package com.beibeiMajor.system.service;

import java.util.List;
import com.beibeiMajor.system.domain.WebDotaHero;

/**
 * 英雄信息Service接口
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
public interface IWebDotaHeroService 
{
    /**
     * 查询英雄信息
     * 
     * @param heroId 英雄信息ID
     * @return 英雄信息
     */
    public WebDotaHero selectWebDotaHeroById(Long heroId);

    /**
     * 查询英雄信息列表
     * 
     * @param webDotaHero 英雄信息
     * @return 英雄信息集合
     */
    public List<WebDotaHero> selectWebDotaHeroList(WebDotaHero webDotaHero);

    /**
     * 新增英雄信息
     * 
     * @param webDotaHero 英雄信息
     * @return 结果
     */
    public int insertWebDotaHero(WebDotaHero webDotaHero);

    /**
     * 修改英雄信息
     * 
     * @param webDotaHero 英雄信息
     * @return 结果
     */
    public int updateWebDotaHero(WebDotaHero webDotaHero);

    /**
     * 批量删除英雄信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebDotaHeroByIds(String ids);

    /**
     * 删除英雄信息信息
     * 
     * @param heroId 英雄信息ID
     * @return 结果
     */
    public int deleteWebDotaHeroById(Long heroId);
}
