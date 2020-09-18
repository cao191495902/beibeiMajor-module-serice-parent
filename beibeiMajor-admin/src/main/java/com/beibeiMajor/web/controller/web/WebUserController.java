package com.beibeiMajor.web.controller.web;

import com.beibeiMajor.common.annotation.Log;
import com.beibeiMajor.common.core.controller.BaseController;
import com.beibeiMajor.common.core.domain.AjaxResult;
import com.beibeiMajor.common.core.page.TableDataInfo;
import com.beibeiMajor.common.enums.BusinessType;
import com.beibeiMajor.common.utils.StringUtils;
import com.beibeiMajor.common.utils.poi.ExcelUtil;
import com.beibeiMajor.framework.shiro.service.SysPasswordService;
import com.beibeiMajor.framework.util.ShiroUtils;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.service.IWebUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/web/user")
public class WebUserController extends BaseController
{
    private String prefix = "web/user";

    @Autowired
    private IWebUserService webUserService;
    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("web:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    /**
     * 查询用户信息列表
     */
    @RequiresPermissions("web:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WebUser webUser)
    {
        startPage();
        List<WebUser> list = webUserService.selectWebUserList(webUser);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @RequiresPermissions("web:user:export")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WebUser webUser)
    {
        List<WebUser> list = webUserService.selectWebUserList(webUser);
        ExcelUtil<WebUser> util = new ExcelUtil<WebUser>(WebUser.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 新增用户信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用户信息
     */
    @RequiresPermissions("web:user:add")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WebUser webUser) {

        webUser.setSalt(ShiroUtils.randomSalt());
        webUser.setPassword(passwordService.encryptPassword(webUser.getLoginName(), webUser.getPassword(), webUser.getSalt()));
        webUser.setCreateBy(ShiroUtils.getLoginName());

        return toAjax(webUserService.insertWebUser(webUser));
    }

    /**
     * 修改用户信息
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        WebUser webUser = webUserService.selectWebUserById(userId);
        mmap.put("webUser", webUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户信息
     */
    @RequiresPermissions("web:user:edit")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WebUser webUser) {

        //更新用户信息
        webUser = webUserService.getUpdateWebUserInfo(webUser);
        //更新密码
        if (StringUtils.isNotEmpty(webUser.getPassword()) && webUser.getPassword().length() < 20) {
            webUser.setSalt(ShiroUtils.randomSalt());
            webUser.setPassword(passwordService.encryptPassword(webUser.getLoginName(), webUser.getPassword(), webUser.getSalt()));
            webUser.setCreateBy(ShiroUtils.getLoginName());

        }
        return toAjax(webUserService.updateWebUser(webUser));
    }


    /**
     * 删除用户信息
     */
    @RequiresPermissions("web:user:remove")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(webUserService.deleteWebUserByIds(ids));
    }
}
