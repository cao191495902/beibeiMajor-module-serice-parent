package com.beibeiMajor.web.service;

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

    List<WebUserDotaReport> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport);

    List<TopBean> statisticsTopInfoList();

    List<WebUser> getYesTodayTopUser(Long startDate,Long endDate);


    List<MyMatchDetailBean> getMyRecordList(String userId);
}
