package com.changgou.oauth.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作cookie工具类
 */
public class CookieUtil {


    /**
     * 添加cookie
     *
     * @param response
     * @param domain        设置cookie域名
     * @param path          设置cookie路径
     * @param name          设置cookie名称
     * @param value         设置cookie的值，即jti
     * @param maxAge        设置cookie生命周期，以秒为单位
     * @param httpOnly
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);   // 如果需要在同一个一级域名下共享cookie信息，设置此项
        cookie.setPath(path);       // 如果设置为 / ，表示在同一个Tomcat服务器中，多个APP之间共享cookie信息
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }


    /**
     * 根据cookie名称读取cookie
     *
     * @param request
     * @return map<cookieName, cookieValue>
     */
    public static Map<String, String> readCookie(HttpServletRequest request, String... cookieNames) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                for (int i = 0; i < cookieNames.length; i++) {
                    if (cookieNames[i].equals(cookieName)) {
                        cookieMap.put(cookieName, cookieValue);
                    }
                }
            }
        }
        return cookieMap;
    }
}
