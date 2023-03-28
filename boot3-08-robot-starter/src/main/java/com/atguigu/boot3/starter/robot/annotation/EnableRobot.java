package com.atguigu.boot3.starter.robot.annotation;

import com.atguigu.boot3.starter.robot.RobotAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import java.lang.annotation.*;

/**
 * @author lfy
 * @Description
 * @create 2023-04-27 20:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(RobotAutoConfiguration.class)
public @interface EnableRobot {


}
