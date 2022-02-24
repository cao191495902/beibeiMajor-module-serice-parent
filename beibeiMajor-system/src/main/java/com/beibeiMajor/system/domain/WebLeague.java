package com.beibeiMajor.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: caowei
 * @Date: 2022/2/17 10:00
 * @Description: 联赛信息
 */
@Data
public class WebLeague implements Serializable {
    private static final long serialVersionUID = 2648201562045156266L;

    private Integer id;

    private String name;

    private String key;

}
