package com.atguigu.web.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lfy
 * @Description
 * @create 2023-04-14 16:57
 */
@ControllerAdvice //这个类是集中处理所有 @Controller 发生的错误
public class GlobalExceptionHandler {

    /**
     * 1、@ExceptionHandler 标识一个方法处理错误，默认只能处理这个类发生的指定错误
     * 2、@ControllerAdvice 统一处理所有错误
     * @param e
     * @return
     */
    @ResponseBody //对象写出为json
//    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){

        return "Ohho~~~统一处理，原因："+e.getMessage();
    }
}
