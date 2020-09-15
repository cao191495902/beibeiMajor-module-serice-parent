package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.system.domain.WebDotaHero;
import com.beibeiMajor.system.service.IWebDotaHeroService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 英雄信息Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/hero")
public class WebDotaHeroController extends BaseController
{
    private String prefix = "web/hero";

    @Autowired
    private IWebDotaHeroService webDotaHeroService;

    @RequiresPermissions("web:hero:view")
    @GetMapping()
    public String hero()
    {
        return prefix + "/hero";
    }

    /**
     * 查询英雄信息列表
     */
    @RequiresPermissions("web:hero:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebDotaHero webDotaHero)
    {
        startPage();
        List<WebDotaHero> list = webDotaHeroService.selectWebDotaHeroList(webDotaHero);
        return getDataTable(list);
    }

    /**
     * 导出英雄信息列表
     */
    @RequiresPermissions("web:hero:export")
    @Log(title = "英雄信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebDotaHero webDotaHero)
    {
        List<WebDotaHero> list = webDotaHeroService.selectWebDotaHeroList(webDotaHero);
        ExcelUtil<WebDotaHero> util = new ExcelUtil<WebDotaHero>(WebDotaHero.class);
        return util.exportExcel(list, "hero");
    }

    /**
     * 新增英雄信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存英雄信息
     */
    @RequiresPermissions("web:hero:add")
    @Log(title = "英雄信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebDotaHero webDotaHero)
    {
        return toAjax(webDotaHeroService.insertWebDotaHero(webDotaHero));
    }

    /**
     * 修改英雄信息
     */
    @GetMapping("/edit/{heroId}")
    public String edit(@PathVariable("heroId") Long heroId, ModelMap mmap)
    {
        WebDotaHero webDotaHero = webDotaHeroService.selectWebDotaHeroById(heroId);
        mmap.put("webDotaHero", webDotaHero);
        return prefix + "/edit";
    }

    /**
     * 修改保存英雄信息
     */
    @RequiresPermissions("web:hero:edit")
    @Log(title = "英雄信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebDotaHero webDotaHero)
    {
        return toAjax(webDotaHeroService.updateWebDotaHero(webDotaHero));
    }

    /**
     * 删除英雄信息
     */
    @RequiresPermissions("web:hero:remove")
    @Log(title = "英雄信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webDotaHeroService.deleteWebDotaHeroByIds(ids));
    }
}
