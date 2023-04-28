package com.atguigu.boot3.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 21:48
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }
}
