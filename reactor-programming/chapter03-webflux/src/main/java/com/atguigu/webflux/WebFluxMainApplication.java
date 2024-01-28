package com.atguigu.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author lfy
 * @Description
 * @create 2023-12-01 20:52
 */

//@EnableWebFlux // 注解： 开启WebFlux自定义； 禁用WebFLux的默认效果，完全自定义
    // WebFluxAutoConfiguration 的自动配置会生效


//@EnableWebFlux //所有WebFluxAutoConfiguration 配置默认效果全部失效
@SpringBootApplication
public class WebFluxMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxMainApplication.class,args);
    }
}
