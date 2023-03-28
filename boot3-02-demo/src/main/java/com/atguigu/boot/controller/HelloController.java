package com.atguigu.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-03-27 21:05
 */
@RestController
public class HelloController {

    @GetMapping("/haha")
    public String hello(){
        return "Hello";
    }

}
