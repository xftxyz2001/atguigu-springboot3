package com.atguigu.boot3.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 16:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private Long id;
    private String name;
    private String email;
}
