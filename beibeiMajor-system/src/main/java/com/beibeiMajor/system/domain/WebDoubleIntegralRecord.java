package com.beibeiMajor.system.domain;

import com.beibeiMajor.common.annotation.Excel;
import com.beibeiMajor.common.core.domain.BaseEntity;

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

    /** 结算状态 */
    @Excel(name = "结算状态")
    private Boolean settlementStatus;

    private Integer type;

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

    public Boolean getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Boolean settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    @Override
    public String toString() {
        return "WebDoubleIntegralRecord{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", changeTimes=" + changeTimes +
                ", money=" + money +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                ", settlementStatus=" + settlementStatus +
                '}';
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
