package com.atguigu.boot3.crud.entity;

import lombok.Data;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 16:42
 */
@Data
public class Employee {
    private Long id;
    private String empName;
    private Integer age;
    private String email;
}
