package com.beibeiMajor.web.service.impl;

import com.beibeiMajor.web.mapper.dao.WebMatchDetailDao;
import com.beibeiMajor.web.mapper.dao.WebUserDotaReportDao;
import com.beibeiMajor.web.mapper.po.UserAverageDataPo;
import com.beibeiMajor.web.mapper.po.WebUserDotaReportPo;
import com.beibeiMajor.web.mapper.po.WinAndLosePo;
import com.beibeiMajor.web.service.ReportInfoService;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 */
public class ReportInfoServiceImpl implements ReportInfoService {

    @Resource
    WebUserDotaReportDao webUserDotaReportDao;
    @Resource
    WebMatchDetailDao webMatchDetailDao;

    @Override
    public List<WebUserDotaReportPo> handlerMatchInfoToReport() {
        WebUserDotaReportPo webUserDotaReportPo = new WebUserDotaReportPo();
        List<WebUserDotaReportPo> reportPoList = webUserDotaReportDao.queryAll(webUserDotaReportPo);
        //STEP 0 更新用户报表

        List<WinAndLosePo> list = webUserDotaReportDao.getUserWinAndLose();
        if (CollectionUtils.isNotEmpty(list)){
            //STEP 1 计算用户积分
            handlerIntegralOfUsers();
            //STEP 2 计算用户胜率
            list.forEach(item->{
                reportPoList.forEach(reportPo -> {
                    if (reportPo.getUserId().longValue() == item.getAccountId().longValue()){
                        reportPo.setWinRate(item.getWinRate());
                    }
                });
            });
            //STEP 3 场均KDA,场均击杀,场均阵亡,场均助攻,场均GPM,场均XPM
            List<UserAverageDataPo> avgDataList = webUserDotaReportDao.getUserAverageData();
            avgDataList.forEach(item ->{
                reportPoList.forEach(reportPo -> {
                    if (reportPo.getUserId().longValue() == item.getAccountId().longValue()){
                        reportPo.setAverageAssists(item.getAssist());
                        reportPo.setAverageDeaths(item.getDeath());
                        reportPo.setAverageKills(item.getKill());
                        reportPo.setKda(item.getKda());
                        reportPo.setXpPerMin(item.getXpm());
                        reportPo.setGoldPerMin(item.getGpm());
                    }
                });
            });
            //全量更新表
            webUserDotaReportDao.batchUpdate(reportPoList);
        }
        return null;
    }

    public Map<String,Integer[]> handlerIntegralOfUsers(){
        //step 1 先查询当前用户积分
        WebUserDotaReportPo webUserDotaReportPo = new WebUserDotaReportPo();
        List<WebUserDotaReportPo> reportPoList = webUserDotaReportDao.queryAll(webUserDotaReportPo);
        //step 2 按照每把比赛进行计算
        /*webMatchDetailDao.queryAll();*/
        return null;
    }

}
