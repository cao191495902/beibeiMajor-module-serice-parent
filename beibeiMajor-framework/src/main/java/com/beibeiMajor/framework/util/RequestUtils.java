package com.beibeiMajor.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public abstract class RequestUtils {

    /**
     * 从request中获得到字符串数组， 优先级：attribute,parameter
     * @param request
     * @param name
     * @return
     */
    public static String[] getStringArray(HttpServletRequest request, String name, String separator){
        //先从attribute里面取，因为attribute的作用域更小。write by dust@downjoy.com
        String[] args=(String[])request.getAttribute(name);
        if(args==null) {
            if(separator==null) {
                args = request.getParameterValues(name);
            }
            else{
                String s=request.getParameter(name);
                if(s!=null){
                    args=TextUtils.split(s,separator);
                }
            }
        }
        return args;
    }

    public static String[] getStringArray(HttpServletRequest request, String name){
        return getStringArray(request, name, null);
    }

    public static Long[] getLongArray(HttpServletRequest request, String name){
        return getLongArray(request, name, null);
    }

    /**
     * 从请求中获得long数组。优先级：attribute,parameter. 未处理NumberFormatException
     * @param request
     * @param name
     * @return
     */
    public static Long[] getLongArray(HttpServletRequest request, String name, String separator){
        String[] strArray=getStringArray(request, name, separator);
        if(strArray==null){
            return null;
        }
        if(strArray.length==0){
            return new Long[]{};
        }
        Long[] values=new Long[strArray.length];
        for(int i=0; i<values[i]; i++){
            values[i]=Long.valueOf(strArray[i]);
        }
        return values;
    }

    /**
     * 从请求中获得long数组。优先级：attribute,parameter. 未处理NumberFormatException
     * @param request
     * @param name
     * @return
     */
    public static Integer[] getIntegerArray(HttpServletRequest request, String name){
        return getIntegerArray(request,name,null);
    }

    public static Integer[] getIntegerArray(HttpServletRequest request, String name, String separator){
        String[] strArray=getStringArray(request, name,separator);
        if(strArray==null){
            return null;
        }
        if(strArray.length==0){
            return new Integer[]{};
        }
        return convertToIntegerArray(strArray);
    }

    private static Integer[] convertToIntegerArray(String[] strArray){
        Integer[] values=new Integer[strArray.length];
        for(int i=0; i<values.length; i++){
            if(!NumberUtils.isDigits(strArray[i])) {
                throw new NumberFormatException("unexpect number format string:"+strArray[i]);
            }
            values[i] = Integer.valueOf(strArray[i]);
        }
        return values;
    }

    /**
     * 从request中获取字符串参数
     * @param request {@link HttpServletRequest}
     * @param name 参数名称
     * @return
     */
    public static String getString(HttpServletRequest request, String name) {
        String value=request.getParameter(name);
        if(TextUtils.isEmpty(value)) {
            value=(String)request.getAttribute(name);
        }
        return value;
    }

    /**
     * 从request中获取Long参数
     * @param request {@link HttpServletRequest}
     * @param name 参数名称
     * @return
     */
    public static Long getLong(HttpServletRequest request, String name) {
        String value=getString(request, name);
        if(TextUtils.isEmpty(value)) {
            return null;
        }
        return Long.valueOf(value);
    }

    /**
     * 从request中获取整形参数
     * @param request {@link HttpServletRequest}
     * @param name 参数名称
     * @return
     */
    public static Integer getInteger(HttpServletRequest request, String name) {
        String value=getString(request, name);
        if(TextUtils.isEmpty(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * 获取真实IP (去除代理IP和本地IP)
     * @param request {@link HttpServletRequest}
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String unknown="unknown";
        String ip=getSubIp(request);
        if(TextUtils.isEmpty(ip)) {
            return "";
        }
        String tmpIps[]=TextUtils.split(ip, ",");
        for(String tmpIp: tmpIps) {
            if(null == tmpIp) {
                continue;
            }
            tmpIp=tmpIp.trim();
            // 过滤本机IP和内网IP，内网IP地址:10.x.x.x;192.168.x.x;172.16.x.x至172.31.x.x。由于172.16.x.x至172.31.x.x比较少见故不做过滤。
            if(tmpIp.length() != 0 && !unknown.equalsIgnoreCase(tmpIp) && tmpIp.indexOf("127.0.0.1") == -1
                && !tmpIp.startsWith("192.168.") && !tmpIp.startsWith("10.")) {
                return tmpIp;
            }
        }
        return null;
    }

    public static String getIpAddr(HttpServletRequest request)
    {
      String unknown = "unknown";
      String ip = request.getHeader("x-forwarded-for");
      if ((ip == null) || (ip.length() == 0) || (unknown.equalsIgnoreCase(ip))) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if ((ip == null) || (ip.length() == 0) || (unknown.equalsIgnoreCase(ip))) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if ((null != ip) && (ip.length() != 0)) {
        String[] tmpIps = ip.split(",");
        for (String tmpIp : tmpIps) {
          if (null == tmpIp) {
            continue;
          }
          tmpIp = tmpIp.trim();

          if ((tmpIp.length() != 0) 
                  && (!(unknown.equalsIgnoreCase(tmpIp))) 
                  && (tmpIp.indexOf("127.0.0.1") == -1) 
                  && (!(tmpIp.startsWith("192.168."))) 
                  && !(tmpIp.startsWith("10."))
                  && !tmpIp.startsWith("118.144.66")
                  && !tmpIp.startsWith("118.144.65")
                  && !tmpIp.startsWith("211.147.5")
          ){
            return tmpIp;
          }
        }
      }
      return request.getRemoteAddr();
    }
    
    /**
     * 获取IP
     * @param request {@link HttpServletRequest}
     * @return
     */
    public static String getSubIp(HttpServletRequest request) {
        String unknown="unknown";
        String ip=request.getHeader("x-forwarded-for");
        if(ip != null && ip.length() > 0 && !unknown.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip=request.getHeader("Proxy-Client-IP");
        if(ip != null && ip.length() > 0 && !unknown.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip=request.getHeader("WL-Proxy-Client-IP");
        if(ip != null && ip.length() > 0 && !unknown.equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 添加cookie
     * @param response {@link HttpServletResponse}
     * @param name cookie名称
     * @param value cookie值
     * @param ageInSeconds 生命周期 单位为秒
     * @param domain 作用域
     * @param path 路径
     * @param security 是否安全
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int ageInSeconds, String domain,
        String path, boolean security) {
        response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
        if(TextUtils.isNotEmpty(value)) {
            try {
                value=URLEncoder.encode(value, "utf8");
            } catch(UnsupportedEncodingException e) {
            }
        }
        Cookie cookie=new Cookie(name, value);
        cookie.setMaxAge(ageInSeconds);
        path=TextUtils.isEmpty(path) ? "/" : path;
        cookie.setPath(path);
        cookie.setSecure(security);
        if(TextUtils.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param name cookie名称
     */
    public static void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies=request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(int i=0; i < cookies.length; i++) {
                if(cookies[i].getName() != null && name.equals(cookies[i].getName())) {
                    cookies[i].setMaxAge(0);
                    cookies[i].setValue("");
                    response.addCookie(cookies[i]);
                }
            }
        }
    }

    /**
     * 获取cookie
     * @param request {@link HttpServletRequest}
     * @param name cookie名称
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies=request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie: cookies) {
                if(cookie.getValue() != null && name.equals(cookie.getName())) {
                    String value="";
                    try {
                        value=URLDecoder.decode(cookie.getValue().trim(), "UTF-8");
                    } catch(UnsupportedEncodingException e) {
                    }
                    if(value.indexOf("%") > -1) {
                        return null;
                    }
                    return value;
                }
            }
        }
        return "";
    }

    /**
     * 是否为爬虫
     * @param request
     * @return
     */
    public static boolean isSpider(HttpServletRequest request) {
        return false;
    }

    /**
     * 编码html
     * @param s
     * @return
     */
    public static String encodeHTML(String s) {
        s=TextUtils.replace(s, "&", "&amp;");
        s=TextUtils.replace(s, "<", "&lt;");
        s=TextUtils.replace(s, ">", "&gt;");
        s=TextUtils.replace(s, "\"", "&quot;");
        s=TextUtils.replace(s, "  ", " &nbsp;");
        s=TextUtils.replace(s, String.valueOf((char)9), " &nbsp;");
        s=TextUtils.replace(s, "\n", "<br>");
        return s;
    }

    /**
     * 解码html
     * @param s
     * @return
     */
    public String decodeHTML(String s) {
        s=TextUtils.replace(s, "<br>", "\r\n");
        s=TextUtils.replace(s, "&nbsp;", " ");
        s=TextUtils.replace(s, "&quot;", "\"");
        s=TextUtils.replace(s, "&gt;", ">");
        s=TextUtils.replace(s, "&lt;", "<");
        s=TextUtils.replace(s, "&amp;", "&");
        return s;
    }

    /**
     * 获取当前URL
     * @param request
     * @param notAppendParameterSet
     * @param charset
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static String getCurrentUrl(HttpServletRequest request, Set<String> notAppendParameterSet, String charset)
        throws Exception {
        if(TextUtils.isEmpty(charset)) {
            charset="utf-8";
        }
        StringBuilder url=new StringBuilder().append(request.getRequestURI());
        Map<String, String> paramMap=new HashMap<String, String>();
        Enumeration<String> ep=(Enumeration<String>)request.getParameterNames();
        while(ep.hasMoreElements()) {
            String key=(String)ep.nextElement();
            paramMap.put(key, request.getParameter(key));
        }
        Enumeration<String> ea=(Enumeration<String>)request.getAttributeNames();
        while(ea.hasMoreElements()) {
            String key=(String)ea.nextElement();
            Object value=request.getAttribute(key);
            if(value instanceof String) {
                paramMap.put(key, (String)request.getAttribute(key));
            }
        }

        StringBuilder params=new StringBuilder();
        for(Iterator<String> it=paramMap.keySet().iterator(); it.hasNext();) {
            String key=it.next();
            if(notAppendParameterSet != null && notAppendParameterSet.contains(key)) { // 参数名包含在不添加的map中,不添加
                continue;
            }
            if(params.length() == 0) {
                params.append(key).append("=").append(URLEncoder.encode(paramMap.get(key), "UTF-8"));
            } else {
                params.append("&").append(key).append("=").append(URLEncoder.encode(paramMap.get(key), "UTF-8"));
            }
        }
        if(params.length() > 0 && url.indexOf("?") == -1) {
            url.append("?").append(params);
        }
        return url.toString();
    }

}
