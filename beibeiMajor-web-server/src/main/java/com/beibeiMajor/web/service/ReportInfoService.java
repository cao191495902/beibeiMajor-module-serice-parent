package com.beibeiMajor.web.service;

import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;

import java.util.List;

/**
 * @author lenovo
 */
public interface ReportInfoService {

    List<WebUserDotaReportPo> handlerMatchInfoToReport();
}
