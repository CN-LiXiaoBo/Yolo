package com.sicnu.yolo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @className: Result
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/2 17:28
 */

@Data
@ToString
@Accessors(chain = true)
public class Result {
    private boolean flag;
    private String message;
    private Object data;
    private Result(){

    }
    public static Result SUCCESS(){
        return new Result().setFlag(true);
    }
    public static Result FAIL(){
        return new Result().setFlag(false);
    }
}
