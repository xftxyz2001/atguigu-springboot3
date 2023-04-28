package com.atguigu.boot3.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Security场景的自动配置类：
 * SecurityAutoConfiguration、SpringBootWebSecurityConfiguration、SecurityFilterAutoConfiguration、
 * 1、security的所有配置在 SecurityProperties： 以spring.security开头
 * 2、默认SecurityFilterChain组件：
 *   - 所有请求都需要认证（登录）
 *   - 开启表单登录: spring security提供一个默认登录页，未经登录的所有请求都需要登录
 *   - httpbasic方式登录
 * 3、@EnableWebSecurity 生效
 *   - WebSecurityConfiguration生效：web安全配置
 *   - HttpSecurityConfiguration生效：http安全规则
 *   - @EnableGlobalAuthentication生效：全局认证生效
 *     - AuthenticationConfiguration：认证配置
 */
@SpringBootApplication
public class Boot313SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot313SecurityApplication.class, args);
    }

}
