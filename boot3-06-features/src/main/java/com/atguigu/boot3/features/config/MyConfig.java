package com.atguigu.boot3.features.config;

import com.atguigu.boot3.features.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lfy
 * @Description  java -jar demo.jar --server.port=9999
 * @create 2023-04-21 14:38
 */
//@PropertySource("classpath:aaaa.properties")
@Profile("test")  //只有指定环境被激活整个类的所有配置才能生效
@Configuration
public class MyConfig {


    @Profile("dev")
    @Bean
    public Cat cat(){
        return new Cat();
    }
}
