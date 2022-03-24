package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.system.domain.WebDotaHero;
import com.beibeiMajor.system.domain.WebMatchPlayerInfo;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.service.IWebDotaHeroService;
import com.beibeiMajor.system.service.IWebMatchPlayerInfoService;
import com.beibeiMajor.system.service.IWebUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 比赛玩家详情Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/info")
public class WebMatchPlayerInfoController extends BaseController
{
    private String prefix = "web/info";

    @Autowired
    private IWebMatchPlayerInfoService webMatchPlayerInfoService;
    @Autowired
    private IWebDotaHeroService webDotaHeroService;
    @Autowired
    private IWebUserService webUserService;

    @RequiresPermissions("web:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询比赛玩家详情列表
     */
    @RequiresPermissions("web:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        startPage();
        List<WebMatchPlayerInfo> list = webMatchPlayerInfoService.selectWebMatchPlayerInfoList(webMatchPlayerInfo);
        return getDataTable(list);
    }

    /**
     * 导出比赛玩家详情列表
     */
    @RequiresPermissions("web:info:export")
    @Log(title = "比赛玩家详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        List<WebMatchPlayerInfo> list = webMatchPlayerInfoService.selectWebMatchPlayerInfoList(webMatchPlayerInfo);
        ExcelUtil<WebMatchPlayerInfo> util = new ExcelUtil<WebMatchPlayerInfo>(WebMatchPlayerInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增比赛玩家详情
     */
    @GetMapping("/add")
    public Object add(ModelMap mmap) {
        //返回用户列表
        List<WebUser> userList = webUserService.selectWebUserList(new WebUser());
        mmap.addAttribute("userList", userList);
        //返回英雄列表
        List<WebDotaHero> heroList = webDotaHeroService.selectWebDotaHeroList(new WebDotaHero());
        mmap.addAttribute("heroList", heroList);
        return new ModelAndView("web/info/add");
    }

    /**
     * 新增保存比赛玩家详情
     */
    @RequiresPermissions("web:info:add")
    @Log(title = "比赛玩家详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        return toAjax(webMatchPlayerInfoService.insertWebMatchPlayerInfo(webMatchPlayerInfo));
    }

    /**
     * 修改比赛玩家详情
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        WebMatchPlayerInfo webMatchPlayerInfo = webMatchPlayerInfoService.selectWebMatchPlayerInfoById(id);
        mmap.put("webMatchPlayerInfo", webMatchPlayerInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存比赛玩家详情
     */
    @RequiresPermissions("web:info:edit")
    @Log(title = "比赛玩家详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebMatchPlayerInfo webMatchPlayerInfo)
    {
        return toAjax(webMatchPlayerInfoService.updateWebMatchPlayerInfo(webMatchPlayerInfo));
    }

    /**
     * 删除比赛玩家详情
     */
    @RequiresPermissions("web:info:remove")
    @Log(title = "比赛玩家详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webMatchPlayerInfoService.deleteWebMatchPlayerInfoByIds(ids));
    }
}
