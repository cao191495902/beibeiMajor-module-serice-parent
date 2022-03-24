package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.system.domain.WebMatchDetail;
import com.beibeiMajor.system.service.IWebMatchDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 比赛详情Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/detail")
public class WebMatchDetailController extends BaseController
{
    private String prefix = "web/detail";

    @Autowired
    private IWebMatchDetailService webMatchDetailService;

    @RequiresPermissions("web:detail:view")
    @GetMapping()
    public String detail()
    {
        return prefix + "/detail";
    }

    /**
     * 查询比赛详情列表
     */
    @RequiresPermissions("web:detail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebMatchDetail webMatchDetail)
    {
        startPage();
        List<WebMatchDetail> list = webMatchDetailService.selectWebMatchDetailList(webMatchDetail);
        return getDataTable(list);
    }

    /**
     * 导出比赛详情列表
     */
    @RequiresPermissions("web:detail:export")
    @Log(title = "比赛详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebMatchDetail webMatchDetail)
    {
        List<WebMatchDetail> list = webMatchDetailService.selectWebMatchDetailList(webMatchDetail);
        ExcelUtil<WebMatchDetail> util = new ExcelUtil<WebMatchDetail>(WebMatchDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 新增比赛详情
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存比赛详情
     */
    @RequiresPermissions("web:detail:add")
    @Log(title = "比赛详情" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebMatchDetail webMatchDetail,
                              @RequestParam(value = "startDate" , required = false) Date startDate) {
        if (startDate != null)
            webMatchDetail.setStartTime(startDate.getTime()/1000);
        webMatchDetail.setHumanPlayers(10L);
        return toAjax(webMatchDetailService.insertWebMatchDetail(webMatchDetail));
    }

    /**
     * 修改比赛详情
     */
    @GetMapping("/edit/{matchId}")
    public String edit(@PathVariable("matchId") Long matchId, ModelMap mmap)
    {
        WebMatchDetail webMatchDetail = webMatchDetailService.selectWebMatchDetailById(matchId);
        mmap.put("webMatchDetail", webMatchDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存比赛详情
     */
    @RequiresPermissions("web:detail:edit")
    @Log(title = "比赛详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebMatchDetail webMatchDetail)
    {
        return toAjax(webMatchDetailService.updateWebMatchDetail(webMatchDetail));
    }

    /**
     * 删除比赛详情
     */
    @RequiresPermissions("web:detail:remove")
    @Log(title = "比赛详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webMatchDetailService.deleteWebMatchDetailByIds(ids));
    }
}
