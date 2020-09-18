package com.beibeiMajor.system.domain;

import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 web_double_integral_record
 * 
 * @author ruoyi
 * @date 2020-09-18
 */
public class WebDoubleIntegralRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 用户账户 */
    @Excel(name = "用户账户")
    private Long accountId;

    /** 更变次数 1 加1次 -1 减1次 */
    @Excel(name = "更变次数 1 加1次 -1 减1次")
    private Long changeTimes;

    /** 增加次数时充值的金额 */
    @Excel(name = "增加次数时充值的金额")
    private BigDecimal money;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long createdTime;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAccountId(Long accountId) 
    {
        this.accountId = accountId;
    }

    public Long getAccountId() 
    {
        return accountId;
    }
    public void setChangeTimes(Long changeTimes) 
    {
        this.changeTimes = changeTimes;
    }

    public Long getChangeTimes() 
    {
        return changeTimes;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setCreatedTime(Long createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Long getCreatedTime() 
    {
        return createdTime;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accountId", getAccountId())
            .append("changeTimes", getChangeTimes())
            .append("money", getMoney())
            .append("remark", getRemark())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .toString();
    }
}
