package com.beibeiMajor.web.service;

import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;

import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 */
public interface ReportInfoService {

    List<WebUserDotaReportPo> handlerMatchInfoToReport();

    List<WebUserDotaReport> selectWebUserDotaReportList(WebUserDotaReport webUserDotaReport);

    Map<String, List<Map<String, Double>>> statisticsTopInfoList();

}
