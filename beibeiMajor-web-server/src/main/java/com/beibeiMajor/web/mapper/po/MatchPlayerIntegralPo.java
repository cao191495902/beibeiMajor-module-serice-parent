package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenovo
 */
@Data
public class MatchPlayerIntegralPo implements Serializable {

    private static final long serialVersionUID = -40979901308728979L;

    private Long matchId;
    private Long accountId;
    private Integer beforeIntegral;
    private Integer afterIntegral;

}
