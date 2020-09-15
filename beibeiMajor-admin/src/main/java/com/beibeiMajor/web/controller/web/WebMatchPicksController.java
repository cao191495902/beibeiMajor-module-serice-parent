package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.system.domain.WebMatchPicks;
import com.beibeiMajor.system.service.IWebMatchPicksService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 比赛bpController
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/picks")
public class WebMatchPicksController extends BaseController
{
    private String prefix = "web/picks";

    @Autowired
    private IWebMatchPicksService webMatchPicksService;

    @RequiresPermissions("web:picks:view")
    @GetMapping()
    public String picks()
    {
        return prefix + "/picks";
    }

    /**
     * 查询比赛bp列表
     */
    @RequiresPermissions("web:picks:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebMatchPicks webMatchPicks)
    {
        startPage();
        List<WebMatchPicks> list = webMatchPicksService.selectWebMatchPicksList(webMatchPicks);
        return getDataTable(list);
    }

    /**
     * 导出比赛bp列表
     */
    @RequiresPermissions("web:picks:export")
    @Log(title = "比赛bp", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebMatchPicks webMatchPicks)
    {
        List<WebMatchPicks> list = webMatchPicksService.selectWebMatchPicksList(webMatchPicks);
        ExcelUtil<WebMatchPicks> util = new ExcelUtil<WebMatchPicks>(WebMatchPicks.class);
        return util.exportExcel(list, "picks");
    }

    /**
     * 新增比赛bp
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存比赛bp
     */
    @RequiresPermissions("web:picks:add")
    @Log(title = "比赛bp", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebMatchPicks webMatchPicks)
    {
        return toAjax(webMatchPicksService.insertWebMatchPicks(webMatchPicks));
    }

    /**
     * 修改比赛bp
     */
    @GetMapping("/edit/{matchId}")
    public String edit(@PathVariable("matchId") Long matchId, ModelMap mmap)
    {
        WebMatchPicks webMatchPicks = webMatchPicksService.selectWebMatchPicksById(matchId);
        mmap.put("webMatchPicks", webMatchPicks);
        return prefix + "/edit";
    }

    /**
     * 修改保存比赛bp
     */
    @RequiresPermissions("web:picks:edit")
    @Log(title = "比赛bp", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebMatchPicks webMatchPicks)
    {
        return toAjax(webMatchPicksService.updateWebMatchPicks(webMatchPicks));
    }

    /**
     * 删除比赛bp
     */
    @RequiresPermissions("web:picks:remove")
    @Log(title = "比赛bp", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webMatchPicksService.deleteWebMatchPicksByIds(ids));
    }
}
