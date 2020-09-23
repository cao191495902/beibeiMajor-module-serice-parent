package com.beibeiMajor.web.mapper.dao;

import com.beibeiMajor.web.mapper.po.DoubleAccountPo;
import com.beibeiMajor.web.mapper.po.WebMatchDetailPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    /**
     * 查询全部未结算比赛结合，并按照开始时间升序排序
     *
     * @return 比赛信息集合
     */
    List<WebMatchDetailPo> queryAllUnsettledMatch();

    /**
     * 改变比赛结算状态
     *
     * @return 比赛信息集合
     */
    Boolean changeMatchStatus(List<Long> updateMatchList);

    /**
     * 查看所有玩家的最后玩耍时间
     *
     * @return Map<玩家ID，最后玩耍时间>
     */
    List<Map<Long, Long>> getLastPlayTime();

    Boolean batchUpdateDoubleAccount(List<DoubleAccountPo> list);
}