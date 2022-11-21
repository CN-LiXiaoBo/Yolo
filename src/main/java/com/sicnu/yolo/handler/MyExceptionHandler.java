package com.sicnu.yolo.handler;

import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;
/**
 * @className: MyExceptionHandler
 * @description: 全局异常处理类
 * @author: 热爱生活の李
 * @since: 2022/7/4 17:29
 */
@RestControllerAdvice
public class MyExceptionHandler {

    //对象校验失败时的异常
    @ExceptionHandler(BindException.class)
    public Result MethodArgumentNotValidExceptionHandler(BindException e){
        //获取到错误信息
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.FAIL().setMessage(msg);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result MethodArgumentNotValidExceptionHandler2(MethodArgumentNotValidException e){
        //获取到错误信息
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.FAIL().setMessage(msg);
    }
    //单个参数校验时失败异常
    @ExceptionHandler(ConstraintViolationException.class)
    public Result ConstraintViolationExceptionHandler(ConstraintViolationException e){
        String msg = e.getMessage();
        return Result.FAIL().setMessage(msg.split(" ")[1]);
    }

    //自定义异常
    @ExceptionHandler(MyException.class)
    public Result MyExceptionHandler(MyException e){
        return Result.FAIL().setMessage(e.getMsg());
    }
}
