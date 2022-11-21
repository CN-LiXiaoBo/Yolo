package com.sicnu.yolo.utils;

import org.springframework.util.DigestUtils;

/**
 * @className: MD5Utils
 * @description: MD5加密
 * @author: 热爱生活の李
 * @since: 2022/7/4 17:56
 */
public class MD5Utils {
    public static String Md5(String key,String salt){
        String msg = key+salt;
        if(msg == null){return null;}
        return DigestUtils.md5DigestAsHex(msg.getBytes());
    }
}
