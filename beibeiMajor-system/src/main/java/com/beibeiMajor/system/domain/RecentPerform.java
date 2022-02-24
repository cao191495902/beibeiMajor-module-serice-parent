package com.beibeiMajor.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: caowei
 * @Date: 2022/2/17 10:00
 * @Description: 近期表现
 */
@Data
public class RecentPerform implements Serializable {
    private static final long serialVersionUID = 2648201562045156266L;

    private String text;

    private String value;

    private String max;


}
