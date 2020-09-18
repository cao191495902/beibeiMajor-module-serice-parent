package com.beibeiMajor.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beibeiMajor.common.constant.UserConstants;
import com.beibeiMajor.common.core.text.Convert;
import com.beibeiMajor.common.utils.DateUtils;
import com.beibeiMajor.system.domain.WebUser;
import com.beibeiMajor.system.mapper.WebUserMapper;
import com.beibeiMajor.system.service.IWebUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class WebUserServiceImpl implements IWebUserService
{
    @Autowired
    private WebUserMapper webUserMapper;

    @Value("${customization.leagueMatchId}")
    private String leagueMatch;

    @Value("${customization.key}")
    private String key;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息ID
     * @return 用户信息
     */
    @Override
    public WebUser selectWebUserById(Long userId)
    {
        return webUserMapper.selectWebUserById(userId);
    }

    /**
     * 查询用户信息列表
     * 
     * @param webUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<WebUser> selectWebUserList(WebUser webUser)
    {
        return webUserMapper.selectWebUserList(webUser);
    }

    /**
     * 新增用户信息
     * 
     * @param webUser 用户信息
     * @return 结果
     */
    @Override
    public int insertWebUser(WebUser webUser)
    {
        webUser.setCreateTime(DateUtils.getNowDate());
        return webUserMapper.insertWebUser(webUser);
    }

    /**
     * 修改用户信息
     * 
     * @param webUser 用户信息
     * @return 结果
     */
    @Override
    public int updateWebUser(WebUser webUser)
    {
        webUser.setUpdateTime(DateUtils.getNowDate());
        return webUserMapper.updateWebUser(webUser);
    }

    /**
     * 删除用户信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWebUserByIds(String ids)
    {
        return webUserMapper.deleteWebUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteWebUserById(Long userId)
    {
        return webUserMapper.deleteWebUserById(userId);
    }

    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = webUserMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public boolean registerUser(WebUser user) {
        return webUserMapper.insertWebUser(user) > 0;
    }

    @Override
    public WebUser selectUserByLoginName(String username) {
        return webUserMapper.selectUserByLoginName(username);
    }

    @Override
    public WebUser getUpdateWebUserInfo(WebUser webUser) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002?steamids=" + webUser.getSteamId() + "&key=" + key);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String execute;
        try {
            execute = client.execute(httpGet, handler);
            Map<String,Object> json = (Map) JSONObject.parse(execute);
            if(json.size() > 0 && json.get("response") != null){
                JSONObject result = (JSONObject)json.get("response");
                JSONArray matches = (JSONArray)result.get("players");
                List<WebUser> list = JSONObject.parseArray(matches.toJSONString(), WebUser.class);
                if(!CollectionUtils.isEmpty(list)){
                   WebUser newWeb = list.get(0);
                   webUser.setAvatar(newWeb.getAvatar());
                   webUser.setLoginName(newWeb.getLoginName());
                   webUser.setNickName(newWeb.getNickName());
                   webUser.setPhone(newWeb.getPhone());
                }
                return webUser;

            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void selectInsertWebUser() {
        //先批量igore插入
        webUserMapper.insertByMatchDetails();
        //更新steam信息，每晚上更新一次 防止修改dota2名称，这里面没更新到
        List<WebUser> list = webUserMapper.getAllWebUser();
        for (WebUser user : list) {
            user = getUpdateWebUserInfo(user);
            webUserMapper.updateWebUser(user);
        }
    }
}
