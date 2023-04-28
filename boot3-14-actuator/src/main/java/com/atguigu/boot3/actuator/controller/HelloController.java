package com.atguigu.boot3.actuator.controller;

import com.atguigu.boot3.actuator.component.MyHahaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 23:07
 */
@RestController
public class HelloController {

    @Autowired
    MyHahaComponent myHahaComponent;

    @GetMapping("/hello")
    public String hello(){
        //业务调用
        myHahaComponent.hello();
        return "哈哈哈";
    }
}
