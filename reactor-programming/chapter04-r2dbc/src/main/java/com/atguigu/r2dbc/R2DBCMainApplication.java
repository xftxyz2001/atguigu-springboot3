package com.atguigu.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 20:59
 *
 *
 * SpringBoot 对r2dbc的自动配置
 * 1、R2dbcAutoConfiguration:   主要配置连接工厂、连接池
 *
 * 2、R2dbcDataAutoConfiguration： 主要给用户提供了 R2dbcEntityTemplate 可以进行CRUD操作
 *      R2dbcEntityTemplate: 操作数据库的响应式客户端；提供CruD api ; RedisTemplate XxxTemplate
 *      数据类型映射关系、转换器、自定义R2dbcCustomConversions 转换器组件
 *      数据类型转换：int，Integer；  varchar，String；  datetime，Instant
 *
 *
 *
 * 3、R2dbcRepositoriesAutoConfiguration： 开启Spring Data声明式接口方式的CRUD；
 *      mybatis-plus： 提供了 BaseMapper，IService；自带了CRUD功能；
 *      Spring Data：  提供了基础的CRUD接口，不用写任何实现的情况下，可以直接具有CRUD功能；
 *
 *
 * 4、R2dbcTransactionManagerAutoConfiguration： 事务管理
 *
 */


@SpringBootApplication
public class R2DBCMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2DBCMainApplication.class,args);
    }
}
