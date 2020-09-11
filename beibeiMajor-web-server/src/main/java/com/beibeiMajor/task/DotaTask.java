package com.beibeiMajor.task;

import com.beibeiMajor.common.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("dotaTask")
public class DotaTask {



    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void getGameInfo(){

    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
}
