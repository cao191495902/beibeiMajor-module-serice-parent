package com.beibeiMajor.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beibeiMajor.system.mapper.WebDotaHeroMapper;
import com.beibeiMajor.system.domain.WebDotaHero;
import com.beibeiMajor.system.service.IWebDotaHeroService;
import com.beibeiMajor.common.core.text.Convert;

/**
 * 英雄信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebDotaHeroServiceImpl implements IWebDotaHeroService 
{
    @Autowired
    private WebDotaHeroMapper webDotaHeroMapper;

    /**
     * 查询英雄信息
     * 
     * @param heroId 英雄信息ID
     * @return 英雄信息
     */
    @Override
    public WebDotaHero selectWebDotaHeroById(Long heroId)
    {
        return webDotaHeroMapper.selectWebDotaHeroById(heroId);
    }

    /**
     * 查询英雄信息列表
     * 
     * @param webDotaHero 英雄信息
     * @return 英雄信息
     */
    @Override
    public List<WebDotaHero> selectWebDotaHeroList(WebDotaHero webDotaHero)
    {
        return webDotaHeroMapper.selectWebDotaHeroList(webDotaHero);
    }

    /**
     * 新增英雄信息
     * 
     * @param webDotaHero 英雄信息
     * @return 结果
     */
    @Override
    public int insertWebDotaHero(WebDotaHero webDotaHero)
    {
        return webDotaHeroMapper.insertWebDotaHero(webDotaHero);
    }

    /**
     * 修改英雄信息
     * 
     * @param webDotaHero 英雄信息
     * @return 结果
     */
    @Override
    public int updateWebDotaHero(WebDotaHero webDotaHero)
    {
        return webDotaHeroMapper.updateWebDotaHero(webDotaHero);
    }

    /**
     * 删除英雄信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebDotaHeroByIds(String ids)
    {
        return webDotaHeroMapper.deleteWebDotaHeroByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除英雄信息信息
     * 
     * @param heroId 英雄信息ID
     * @return 结果
     */
    @Override
    public int deleteWebDotaHeroById(Long heroId)
    {
        return webDotaHeroMapper.deleteWebDotaHeroById(heroId);
    }
}
