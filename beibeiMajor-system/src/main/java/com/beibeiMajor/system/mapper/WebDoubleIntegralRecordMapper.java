package com.beibeiMajor.system.mapper;

import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-18
 */
public interface WebDoubleIntegralRecordMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public WebDoubleIntegralRecord selectWebDoubleIntegralRecordById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param webDoubleIntegralRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WebDoubleIntegralRecord> selectWebDoubleIntegralRecordList(WebDoubleIntegralRecord webDoubleIntegralRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param webDoubleIntegralRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertWebDoubleIntegralRecord(WebDoubleIntegralRecord webDoubleIntegralRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param webDoubleIntegralRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateWebDoubleIntegralRecord(WebDoubleIntegralRecord webDoubleIntegralRecord);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteWebDoubleIntegralRecordById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebDoubleIntegralRecordByIds(String[] ids);


    void insertUpdateToDefaultDoubleTimesRecord();

    WebDoubleIntegralRecord selectByTodayAndAccountId(@Param("accountId")Long accountId, @Param("start")long start, @Param("end")long end);

    List<WebDoubleIntegralRecord> selectUnsettledRecordList();

    Boolean batchUpdateDoubleAccount(List<Long> id);
}
