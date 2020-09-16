package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenovo
 */
@Data
public class UserAverageDataPo implements Serializable {
    private static final long serialVersionUID = -63024680516901733L;

    private Long accountId;
    private String nickName;
    private Integer gpm;
    private Integer xpm;
    private Double kill;
    private Double death;
    private Double assist;
    private Double kda;
}
