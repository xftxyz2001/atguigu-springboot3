package com.atguigu.boot3.crud.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 17:42
 */
@Configuration
public class ApiUiConfig {

    /**
     * 分组设置
     * @return
     */
    @Bean
    public GroupedOpenApi empApi() {
        return GroupedOpenApi.builder()
                .group("员工管理")
                .pathsToMatch("/emp/**","/emps")
                .build();
    }
    @Bean
    public GroupedOpenApi deptApi() {
        return GroupedOpenApi.builder()
                .group("部门管理")
                .pathsToMatch("/dept/**","/depts")
                .build();
    }

    @Bean
    public OpenAPI docsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringBoot3-CRUD API")
                        .description("专门测试接口文件")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("哈哈 Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
