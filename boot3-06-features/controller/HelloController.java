package com.atguigu.boot3.aot.controller;

import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-05-16 10:08
 */
@RestController
public class HelloController {


    @GetMapping("/")
    public String hello(){
        return "Hello， Spring Boot AOT!!!";
    }
}
