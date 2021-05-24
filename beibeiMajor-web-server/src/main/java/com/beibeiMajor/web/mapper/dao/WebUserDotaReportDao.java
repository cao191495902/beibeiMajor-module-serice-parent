package com.beibeiMajor.web.mapper.dao;

import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.web.mapper.po.UserAverageDataPo;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;
import com.beibeiMajor.web.mapper.po.WinAndLosePo;
import com.beibeiMajor.web.service.dto.MyMatchDetailBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户积分表(WebUserDotaReport)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-15 15:44:17
 */
@Mapper
@Repository
public interface WebUserDotaReportDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    WebUserDotaReportPo queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<WebUserDotaReportPo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param webUserDotaReportPo 实例对象
     * @return 对象列表
     */
    List<WebUserDotaReportPo> queryAll(WebUserDotaReportPo webUserDotaReportPo);

    /**
     * 新增数据
     *
     * @param webUserDotaReportPo 实例对象
     * @return 影响行数
     */
    int insert(WebUserDotaReportPo webUserDotaReportPo);

    /**
     * 修改数据
     *
     * @param webUserDotaReportPo 实例对象
     * @return 影响行数
     */
    int update(WebUserDotaReportPo webUserDotaReportPo);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    List<WinAndLosePo> getUserWinAndLose();

    List<UserAverageDataPo> getUserAverageData();

    Boolean batchUpdate(List<WebUserDotaReportPo> reportPoList);

    Boolean batchInsert(List<WebUserDotaReportPo> reportPoList);

    List<WebUserDotaReportPo> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport);

    List<String> getHighestPointsTop(@Param("topLimit") int i,@Param("desc")String desc);

    List<String> getHighestWinPerTop(@Param("topLimit") int i,@Param("desc")String desc);

    List<String> getHighestKDATop(@Param("topLimit") int i,@Param("desc")String desc);

    List<String> getLongestWinningStreakTop(@Param("topLimit") int i,@Param("desc")String desc);

    List<String> getHighestKillsPerGameTop(@Param("topLimit") int i,@Param("desc")String desc);

    List<String> getLeastDeathPerGameTop(@Param("topLimit") int i,@Param("desc")String desc);

    List<String> getHighestAssistsPerGameTop(@Param("topLimit") int i,@Param("desc")String desc);

    List<WebUser> getYesTodayTopUser(@Param("startDate")Long startDate, @Param("endDate")Long endDate);

    List<MyMatchDetailBean> getMyRecordList(@Param("accountId") String userId);

    List<String> getWarRateTop(@Param("topLimit")int i, @Param("desc")String desc);

    List<String> getHeroCountTop(@Param("topLimit")int i, @Param("desc")String desc);

    List<String> getheroWinRateTop(@Param("topLimit")int i, @Param("desc")String desc);

    List<String> heroRateTop(@Param("topLimit")int i, @Param("desc")String desc);
}