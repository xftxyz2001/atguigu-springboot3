package com.atguigu.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 21:14
 */

/**
 * Spring Security 默认行为： 所有请求都需要登录才能访问
 * 1、SecurityAutoConfiguration：  以前
 *          导入 SecurityFilterChain 组件： 默认所有请求都需要登录才可以访问、默认登录页
 *
 * 2、SecurityFilterAutoConfiguration：
 * 3、ReactiveSecurityAutoConfiguration：
 *          导入 ServerHttpSecurityConfiguration 配置：注解导入 ServerHttpSecurityConfiguration
 * 4、MethodSecurityAspectJAutoProxyRegistrar：
 */

@SpringBootApplication
public class SecurityDemoMainApplicatioin {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoMainApplicatioin.class,args);
    }
}
