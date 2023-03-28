package com.atguigu.boot3.starter.robot;

import com.atguigu.boot3.starter.robot.controller.RobotController;
import com.atguigu.boot3.starter.robot.properties.RobotProperties;
import com.atguigu.boot3.starter.robot.service.RobotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lfy
 * @Description
 * @create 2023-04-27 20:15
 */
//给容器中导入Robot功能要用的所有组件
@Import({RobotProperties.class, RobotService.class})
@Configuration
public class RobotAutoConfiguration {

    @Bean //把组件导入到容器中
    public RobotController robotController(){
        return new RobotController();
    }


}
