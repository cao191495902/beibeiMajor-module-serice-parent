package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenovo
 */
@Data
public class DoubleAccountPo implements Serializable {
    private static final long serialVersionUID = -63024680546901733L;

    private Long matchId;
    private String accountIds;
}
