package com.beibeiMajor.web.mapper.dao;

import com.beibeiMajor.web.mapper.po.WebMatchPicksPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (WebMatchPicks)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-14 11:15:44
 */
@Mapper
@Repository
public interface WebMatchPicksDao {

    /**
     * 通过ID查询单条数据
     *
     * @param matchId 主键
     * @return 实例对象
     */
    WebMatchPicksPo queryById(Integer matchId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebMatchPicksPo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webMatchPicksPo 实例对象
     * @return 对象列表
     */
    List<WebMatchPicksPo> queryAll(WebMatchPicksPo webMatchPicksPo);

    /**
     * 新增数据
     *
     * @param webMatchPicksPo 实例对象
     * @return 影响行数
     */
    int insert(WebMatchPicksPo webMatchPicksPo);

    /**
     * 修改数据
     *
     * @param webMatchPicksPo 实例对象
     * @return 影响行数
     */
    int update(WebMatchPicksPo webMatchPicksPo);

    /**
     * 通过主键删除数据
     *
     * @param matchId 主键
     * @return 影响行数
     */
    int deleteById(Integer matchId);

    /**
     * 批量新增数据
     *
     * @param picksList 实例对象集合
     * @return 影响行数
     */
    int batchInsert(List<WebMatchPicksPo> picksList);
}