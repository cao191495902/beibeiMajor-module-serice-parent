package com.beibeiMajor.web.mapper.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenovo
 */
@Data
public class PlayerWinOrLosePo implements Serializable {

    private static final long serialVersionUID = -40979901308728979L;

    private Long matchId;
    private String winArray;
    private String loseArray;
    private String doubleAccount;
}
