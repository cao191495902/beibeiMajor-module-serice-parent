package com.beibeiMajor.system.service;

import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2020-09-18
 */
public interface IWebDoubleIntegralRecordService 
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
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWebDoubleIntegralRecordByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteWebDoubleIntegralRecordById(Long id);

    WebDoubleIntegralRecord selectByTodayAndAccountId(Long accountId, long start, long end);

    /**
     * 查询未结算的记录
     *
     * @return 结果
     */
    List<WebDoubleIntegralRecord> selectUnsettledRecordList();

    Boolean batchUpdateDoubleAccount(List<Long> updateList);
}
