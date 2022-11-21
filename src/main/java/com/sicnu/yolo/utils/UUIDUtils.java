package com.sicnu.yolo.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @className: UUIDUtils
 * @description: 生成随机的UUID
 * @author: 热爱生活の李
 * @since: 2022/7/4 17:53
 */
public class UUIDUtils {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
