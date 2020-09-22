package com.beibeiMajor.web.service.dto;

import lombok.Data;

/**
 * 积分数据显示dto
 */
@Data
public class TopBean {

  private String integral;

  private String winRate;

  private String winningStreak;

  private String killsPerGame;

  private String deathPerGame;

  private String assistsPerGame;

  private String kad;
}
