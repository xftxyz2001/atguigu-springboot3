package com.atguigu.boot3.core;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


//@EnableWebMvc //全面接管SpringMVC，禁用所有mvc底层的自动配置
//@EnableAsync //开启异步
//@EnableScheduling //开启定时任务
@SpringBootApplication //SPI
public class Boot307CoreApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Boot307CoreApplication.class);

        //参数设置
//        application.addInitializers();

        application.run(args);
//        SpringApplication.run(Boot307CoreApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(){

        return  args -> {
            System.out.println("===ApplicationRunner 运行了.....");
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return  args -> {
            System.out.println("===CommandLineRunner 运行了.....");
        };
    }

}
