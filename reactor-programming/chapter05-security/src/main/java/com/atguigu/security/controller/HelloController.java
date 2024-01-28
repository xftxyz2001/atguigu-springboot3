package com.atguigu.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 21:31
 */
@RestController
public class HelloController {


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/hello")
    public Mono<String> hello(){

        return Mono.just("hello world!");
    }


    // 角色 haha： ROLE_haha：角色
    // 没有ROLE 前缀是权限

    //复杂的SpEL表达式
    @PreAuthorize("hasRole('delete')")
    @GetMapping("/world")
    public Mono<String> world(){
        return Mono.just("world!!!");
    }
}
