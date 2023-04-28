package com.atguigu.boot3.aot.controller;

import com.atguigu.boot3.aot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * @author lfy
 * @Description
 * @create 2023-05-16 11:44
 */
@RestController
public class HelloController {

//    @Autowired
//    HelloService helloService;

    @GetMapping("/")
    public String hello(){

        //反射调用
        Object invoke = "sayHello";
        try {
            //1、反射获取sayHello方法
            Method sayHello = HelloService.class.getMethod(invoke.toString());
            //2、利用反射获取无参构造器，并创建出对象
            HelloService instance = HelloService.class.getConstructor().newInstance();
            //3、反射执行 instance 的 sayHello 方法
            invoke = sayHello.invoke(instance);
            //4、新版的 GraalVM 可以处理反射，直接编译了。
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Hello, Spring Boot AOT!!! " + invoke.toString();
    }
}
