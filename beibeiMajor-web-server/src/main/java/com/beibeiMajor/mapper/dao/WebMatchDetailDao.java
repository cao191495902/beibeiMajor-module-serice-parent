package com.beibeiMajor.mapper.dao;

import com.beibeiMajor.mapper.po.WebMatchDetailPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (WebMatchDetail)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-14 10:47:45
 */
@Mapper
@Repository
public interface WebMatchDetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param matchId 主键
     * @return 实例对象
     */
    WebMatchDetailPo queryById(Integer matchId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebMatchDetailPo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webMatchDetailPo 实例对象
     * @return 对象列表
     */
    List<WebMatchDetailPo> queryAll(WebMatchDetailPo webMatchDetailPo);

    /**
     * 新增数据
     *
     * @param webMatchDetailPo 实例对象
     * @return 影响行数
     */
    int insert(WebMatchDetailPo webMatchDetailPo);

    /**
     * 修改数据
     *
     * @param webMatchDetailPo 实例对象
     * @return 影响行数
     */
    int update(WebMatchDetailPo webMatchDetailPo);

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
     * @param detailList 实例对象集合
     * @return 影响行数
     */
    int batchInsert(List<WebMatchDetailPo> detailList);

    /**
     * 查询全部比赛Id
     *
     * @return 比赛ID集合
     */
    List<Long> queryAllId();

}