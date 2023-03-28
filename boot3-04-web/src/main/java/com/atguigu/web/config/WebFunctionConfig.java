package com.atguigu.web.config;

import com.atguigu.web.bean.Person;
import com.atguigu.web.biz.UserBizHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @author lfy
 * @Description
 * @create 2023-04-18 21:46
 */

/**
 * 场景：User RESTful - CRUD
 * ● GET /user/1  获取1号用户
 * ● GET /users   获取所有用户
 * ● POST /user  请求体携带JSON，新增一个用户
 * ● PUT /user/1 请求体携带JSON，修改1号用户
 * ● DELETE /user/1 删除1号用户
 */
@Configuration
public class WebFunctionConfig {

    /**
     * 函数式Web：
     * 1、给容器中放一个Bean：类型是 RouterFunction<ServerResponse>，集中所有路由信息
     * 2、每个业务准备一个自己的Handler
     *
     *
     * 核心四大对象
     * 1、RouterFunction： 定义路由信息。发什么请求，谁来处理
     * 2、RequestPredicate：定义请求规则：请求谓语。请求方式（GET、POST）、请求参数
     * 3、ServerRequest：  封装请求完整数据
     * 4、ServerResponse： 封装响应完整数据
     */
    @Bean
    public RouterFunction<ServerResponse> userRoute(UserBizHandler userBizHandler/*这个会被自动注入进来*/){

        return RouterFunctions.route() //开始定义路由信息
                .GET("/user/{id}", RequestPredicates.accept(MediaType.ALL), userBizHandler::getUser)
                .GET("/users", userBizHandler::getUsers)
                .POST("/user", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::saveUser)
                .PUT("/user/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::updateUser)
                .DELETE("/user/{id}", userBizHandler::deleteUser)
                .build();
    }


//    @Bean
//    public RouterFunction<ServerResponse> groupRoute(UserBizHandler userBizHandler/*这个会被自动注入进来*/){
//
//        return RouterFunctions.route() //开始定义路由信息
//                .GET("/user/{id}", RequestPredicates.accept(MediaType.ALL), userBizHandler::getUser)
//                .GET("/users", userBizHandler::getUsers)
//                .POST("/user", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::saveUser)
//                .PUT("/user/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::updateUser)
//                .DELETE("/user/{id}", userBizHandler::deleteUser)
//                .build();
//    }
}
