package com.beibeiMajor.web.mapper.dao;

import com.beibeiMajor.web.mapper.po.WebAbilityUpgradesPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (WebAbilityUpgrades)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-14 11:15:49
 */
@Mapper
@Repository
public interface WebAbilityUpgradesDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WebAbilityUpgradesPo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebAbilityUpgradesPo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webAbilityUpgradesPo 实例对象
     * @return 对象列表
     */
    List<WebAbilityUpgradesPo> queryAll(WebAbilityUpgradesPo webAbilityUpgradesPo);

    /**
     * 新增数据
     *
     * @param webAbilityUpgradesPo 实例对象
     * @return 影响行数
     */
    int insert(WebAbilityUpgradesPo webAbilityUpgradesPo);

    /**
     * 修改数据
     *
     * @param webAbilityUpgradesPo 实例对象
     * @return 影响行数
     */
    int update(WebAbilityUpgradesPo webAbilityUpgradesPo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 批量新增数据
     *
     * @param abilityUpgradesList 实例对象集合
     * @return 影响行数
     */
    int batchInsert(List<WebAbilityUpgradesPo> abilityUpgradesList);

}