package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class WinsOrLoseCountPo implements Serializable  {
    private static final long serialVersionUID = -63024680516901733L;

    private Long accountId;
    private Integer count;
    private Boolean isWin;
}
