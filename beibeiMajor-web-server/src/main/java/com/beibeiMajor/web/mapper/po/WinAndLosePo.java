package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenovo
 */
@Data
public class WinAndLosePo implements Serializable  {
    private static final long serialVersionUID = -63024680516901733L;

    private Long accountId;
    private String nickName;
    private Integer win;
    private Integer lose;
    private Double winRate;
}
