package com.atguigu.boot3.redis.controller;

import com.atguigu.boot3.redis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 15:43
 */
@RestController
public class RedisTestController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //为了后来系统的兼容性，应该所有对象都是以json的方式进行保存
    @Autowired //如果给redis中保存数据会使用默认的序列化机制，导致redis中保存的对象不可视
    RedisTemplate<Object, Object>  redisTemplate;

    @GetMapping("/count")
    public String count(){

        Long hello = stringRedisTemplate.opsForValue().increment("hello");

        //常见数据类型  k: v value可以有很多类型
        //string： 普通字符串 ： redisTemplate.opsForValue()
        //list:    列表：       redisTemplate.opsForList()
        //set:     集合:       redisTemplate.opsForSet()
        //zset:    有序集合:    redisTemplate.opsForZSet()
        //hash：   map结构：    redisTemplate.opsForHash()

        return "访问了【"+hello+"】次";
    }


    @GetMapping("/person/save")
    public String savePerson(){
        Person person = new Person(1L,"张三",18,new Date());

        //1、序列化： 对象转为字符串方式
        redisTemplate.opsForValue().set("person",person);

        return "ok";
    }

    @GetMapping("/person/get")
    public Person getPerson(){
        Person person = (Person) redisTemplate.opsForValue().get("person");

        return person;
    }
}
