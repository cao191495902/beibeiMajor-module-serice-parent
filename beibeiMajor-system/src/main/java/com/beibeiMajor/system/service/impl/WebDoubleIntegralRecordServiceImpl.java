package com.beibeiMajor.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beibeiMajor.system.mapper.WebDoubleIntegralRecordMapper;
import com.beibeiMajor.system.domain.WebDoubleIntegralRecord;
import com.beibeiMajor.system.service.IWebDoubleIntegralRecordService;
import com.beibeiMajor.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-18
 */
@Service
public class WebDoubleIntegralRecordServiceImpl implements IWebDoubleIntegralRecordService 
{
    @Autowired
    private WebDoubleIntegralRecordMapper webDoubleIntegralRecordMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public WebDoubleIntegralRecord selectWebDoubleIntegralRecordById(Long id)
    {
        return webDoubleIntegralRecordMapper.selectWebDoubleIntegralRecordById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param webDoubleIntegralRecord 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<WebDoubleIntegralRecord> selectWebDoubleIntegralRecordList(WebDoubleIntegralRecord webDoubleIntegralRecord)
    {
        return webDoubleIntegralRecordMapper.selectWebDoubleIntegralRecordList(webDoubleIntegralRecord);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param webDoubleIntegralRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertWebDoubleIntegralRecord(WebDoubleIntegralRecord webDoubleIntegralRecord)
    {
        return webDoubleIntegralRecordMapper.insertWebDoubleIntegralRecord(webDoubleIntegralRecord);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param webDoubleIntegralRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateWebDoubleIntegralRecord(WebDoubleIntegralRecord webDoubleIntegralRecord)
    {
        return webDoubleIntegralRecordMapper.updateWebDoubleIntegralRecord(webDoubleIntegralRecord);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebDoubleIntegralRecordByIds(String ids)
    {
        return webDoubleIntegralRecordMapper.deleteWebDoubleIntegralRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteWebDoubleIntegralRecordById(Long id)
    {
        return webDoubleIntegralRecordMapper.deleteWebDoubleIntegralRecordById(id);
    }
}
