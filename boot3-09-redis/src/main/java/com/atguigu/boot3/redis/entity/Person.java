package com.atguigu.boot3.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 16:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private Date birthDay;
}
