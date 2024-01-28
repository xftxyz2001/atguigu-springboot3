package com.atguigu.webflux.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lfy
 * @Description
 * @create 2023-12-01 21:40
 */
//全局异常处理

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ArithmeticException.class)
    public String error(ArithmeticException exception){
        System.out.println("发生了数学运算异常"+exception);

        //返回这些进行错误处理；
//        ProblemDetail：
//        ErrorResponse ：

        return "炸了，哈哈...";
    }
}
