package com.beibeiMajor.web.mapper.dao;

import com.beibeiMajor.web.mapper.po.WebMatchPlayerPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (WebMatchPlayerInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-14 10:32:48
 */
@Mapper
@Repository
public interface WebMatchPlayerInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WebMatchPlayerPo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebMatchPlayerPo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webMatchPlayerPo 实例对象
     * @return 对象列表
     */
    List<WebMatchPlayerPo> queryAll(WebMatchPlayerPo webMatchPlayerPo);

    /**
     * 新增数据
     *
     * @param webMatchPlayerPo 实例对象
     * @return 影响行数
     */
    int insert(WebMatchPlayerPo webMatchPlayerPo);

    /**
     * 修改数据
     *
     * @param webMatchPlayerPo 实例对象
     * @return 影响行数
     */
    int update(WebMatchPlayerPo webMatchPlayerPo);

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
     * @param playerList 实例对象集合
     * @return 影响行数
     *
     */
    int batchInsert(List<WebMatchPlayerPo> playerList);

    /**
     * 查询一场比赛的获胜方ID集合
     *
     * @param matchId 比赛序号
     * @return 玩家集合
     */
    List<Long> getWinPlayerByMatchId(@Param("matchId") Long matchId);

    /**
     * 查询一场比赛的获胜方ID集合
     *
     * @param matchId 比赛序号
     * @return 玩家集合
     */
    List<Long> getLosePlayerByMatchId(@Param("matchId") Long matchId);
}