package com.beibeiMajor.web.mapper.dao;

import com.beibeiMajor.web.mapper.po.WebDotaHeroPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 英雄信息表(WebDotaHero)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-15 11:04:04
 */
@Mapper
@Repository
public interface WebDotaHeroDao {

    /**
     * 通过ID查询单条数据
     *
     * @param heroId 主键
     * @return 实例对象
     */
    WebDotaHeroPo queryById(Object heroId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebDotaHeroPo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webDotaHeroPo 实例对象
     * @return 对象列表
     */
    List<WebDotaHeroPo> queryAll(WebDotaHeroPo webDotaHeroPo);

    /**
     * 新增数据
     *
     * @param webDotaHeroPo 实例对象
     * @return 影响行数
     */
    int insert(WebDotaHeroPo webDotaHeroPo);

    /**
     * 修改数据
     *
     * @param webDotaHeroPo 实例对象
     * @return 影响行数
     */
    int update(WebDotaHeroPo webDotaHeroPo);

    /**
     * 通过主键删除数据
     *
     * @param heroId 主键
     * @return 影响行数
     */
    int deleteById(Object heroId);

    /**
     * 批量新增数据
     *
     * @param lists 实例对象集合
     * @return 影响行数
     */
    void batchInsert(List<WebDotaHeroPo> lists);
}