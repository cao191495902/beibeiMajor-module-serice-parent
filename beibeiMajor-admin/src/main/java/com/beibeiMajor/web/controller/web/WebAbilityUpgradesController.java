package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.system.domain.WebAbilityUpgrades;
import com.beibeiMajor.system.service.IWebAbilityUpgradesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 比赛玩家加点记录Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/upgrades")
public class WebAbilityUpgradesController extends BaseController
{
    private String prefix = "web/upgrades";

    @Autowired
    private IWebAbilityUpgradesService webAbilityUpgradesService;

    @RequiresPermissions("web:upgrades:view")
    @GetMapping()
    public String upgrades()
    {
        return prefix + "/upgrades";
    }

    /**
     * 查询比赛玩家加点记录列表
     */
    @RequiresPermissions("web:upgrades:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebAbilityUpgrades webAbilityUpgrades)
    {
        startPage();
        List<WebAbilityUpgrades> list = webAbilityUpgradesService.selectWebAbilityUpgradesList(webAbilityUpgrades);
        return getDataTable(list);
    }

    /**
     * 导出比赛玩家加点记录列表
     */
    @RequiresPermissions("web:upgrades:export")
    @Log(title = "比赛玩家加点记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebAbilityUpgrades webAbilityUpgrades)
    {
        List<WebAbilityUpgrades> list = webAbilityUpgradesService.selectWebAbilityUpgradesList(webAbilityUpgrades);
        ExcelUtil<WebAbilityUpgrades> util = new ExcelUtil<WebAbilityUpgrades>(WebAbilityUpgrades.class);
        return util.exportExcel(list, "upgrades");
    }

    /**
     * 新增比赛玩家加点记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存比赛玩家加点记录
     */
    @RequiresPermissions("web:upgrades:add")
    @Log(title = "比赛玩家加点记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebAbilityUpgrades webAbilityUpgrades)
    {
        return toAjax(webAbilityUpgradesService.insertWebAbilityUpgrades(webAbilityUpgrades));
    }

    /**
     * 修改比赛玩家加点记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        WebAbilityUpgrades webAbilityUpgrades = webAbilityUpgradesService.selectWebAbilityUpgradesById(id);
        mmap.put("webAbilityUpgrades", webAbilityUpgrades);
        return prefix + "/edit";
    }

    /**
     * 修改保存比赛玩家加点记录
     */
    @RequiresPermissions("web:upgrades:edit")
    @Log(title = "比赛玩家加点记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebAbilityUpgrades webAbilityUpgrades)
    {
        return toAjax(webAbilityUpgradesService.updateWebAbilityUpgrades(webAbilityUpgrades));
    }

    /**
     * 删除比赛玩家加点记录
     */
    @RequiresPermissions("web:upgrades:remove")
    @Log(title = "比赛玩家加点记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webAbilityUpgradesService.deleteWebAbilityUpgradesByIds(ids));
    }
}
