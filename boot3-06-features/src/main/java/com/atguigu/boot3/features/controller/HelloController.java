package com.atguigu.boot3.features.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-04-21 21:08
 */
@RestController
public class HelloController {


    @Value("${haha:啊啊啊啊}")
    String haha;

    @GetMapping("/haha")
    public String haha(){
        return haha;
    }
}
