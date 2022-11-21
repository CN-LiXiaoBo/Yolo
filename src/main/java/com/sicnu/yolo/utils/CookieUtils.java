package com.sicnu.yolo.utils;

import com.sicnu.yolo.entity.exception.MyException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @className: CookieUtils
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/6 22:53
 */
public class CookieUtils {
    public static String getValue(HttpServletRequest request,String name){
        if(request == null || name == null){
            throw new MyException("CookieUtils中的request||name参数不能为空");
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
