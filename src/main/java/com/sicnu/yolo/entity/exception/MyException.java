package com.sicnu.yolo.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @className: MyException
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/4 17:45
 */
@Data
@AllArgsConstructor
public class MyException extends RuntimeException{
    private String msg;
}
