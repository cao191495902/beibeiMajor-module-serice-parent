package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.system.domain.WebUserDotaReport;
import com.beibeiMajor.system.service.IWebUserDotaReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户积分Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/report")
public class WebUserDotaReportController extends BaseController
{
    private String prefix = "web/report";

    @Autowired
    private IWebUserDotaReportService webUserDotaReportService;

    @RequiresPermissions("web:report:view")
    @GetMapping()
    public String report()
    {
        return prefix + "/report";
    }

    /**
     * 查询用户积分列表
     */
    @RequiresPermissions("web:report:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebUserDotaReport webUserDotaReport)
    {
        startPage();
        List<WebUserDotaReport> list = webUserDotaReportService.selectWebUserDotaReportList(webUserDotaReport);
        return getDataTable(list);
    }

    /**
     * 导出用户积分列表
     */
    @RequiresPermissions("web:report:export")
    @Log(title = "用户积分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebUserDotaReport webUserDotaReport)
    {
        List<WebUserDotaReport> list = webUserDotaReportService.selectWebUserDotaReportList(webUserDotaReport);
        ExcelUtil<WebUserDotaReport> util = new ExcelUtil<WebUserDotaReport>(WebUserDotaReport.class);
        return util.exportExcel(list, "report");
    }

    /**
     * 新增用户积分
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户积分
     */
    @RequiresPermissions("web:report:add")
    @Log(title = "用户积分", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebUserDotaReport webUserDotaReport)
    {
        return toAjax(webUserDotaReportService.insertWebUserDotaReport(webUserDotaReport));
    }

    /**
     * 修改用户积分
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        WebUserDotaReport webUserDotaReport = webUserDotaReportService.selectWebUserDotaReportById(userId);
        mmap.put("webUserDotaReport", webUserDotaReport);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户积分
     */
    @RequiresPermissions("web:report:edit")
    @Log(title = "用户积分", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebUserDotaReport webUserDotaReport)
    {
        return toAjax(webUserDotaReportService.updateWebUserDotaReport(webUserDotaReport));
    }

    /**
     * 删除用户积分
     */
    @RequiresPermissions("web:report:remove")
    @Log(title = "用户积分", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webUserDotaReportService.deleteWebUserDotaReportByIds(ids));
    }
}
