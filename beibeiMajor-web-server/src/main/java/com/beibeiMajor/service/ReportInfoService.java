package com.beibeiMajor.service;

import com.beibeiMajor.mapper.po.WebUserDotaReportPo;

import java.util.List;

/**
 * @author lenovo
 */
public interface ReportInfoService {

    List<WebUserDotaReportPo> handlerMatchInfoToReport();
}
