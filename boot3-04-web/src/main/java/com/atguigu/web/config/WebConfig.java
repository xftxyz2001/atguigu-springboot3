package com.atguigu.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lfy
 * @Description
 * @create 2023-04-18 17:04
 */
//全面接管SpringMVC
//@EnableWebMvc  //禁用mvc的默认功能
@Configuration
public class WebConfig implements WebMvcConfigurer {

}
