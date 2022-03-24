package com.beibeiMajor.web.service;

import com.beibeiMajor.system.domain.RecentPerform;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;
import com.beibeiMajor.web.service.dto.MyMatchDetailBean;
import com.beibeiMajor.web.service.dto.TopBean;

import java.util.List;

/**
 * @author lenovo
 */
public interface ReportInfoService {

    List<WebUserDotaReportPo> handlerMatchInfoToReport();

    List<WebUserDotaReportPo> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport,Integer pageNum,Integer pageSize);

    List<TopBean> statisticsTopInfoList(Integer leagueId);

    List<WebUser> getYesTodayTopUser(Long startDate,Long endDate);


    List<MyMatchDetailBean> getMyRecordList(String userId, Integer leagueId, Integer pageNum, Integer pageSize);

    List<TopBean> statisticsLossInfoList(Integer leagueId);

    List<RecentPerform> getRecentPerformList(Long accountId, Integer leagueId);

    void deleteReport();
}
