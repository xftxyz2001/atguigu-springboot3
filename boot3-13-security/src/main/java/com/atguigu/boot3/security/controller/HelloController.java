package com.atguigu.boot3.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 21:45
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello!Spring Security";
    }



    @PreAuthorize("hasAuthority('world_exec')")
    @GetMapping("/world")
    public String world(){
        return "Hello World!!!";
    }
}
