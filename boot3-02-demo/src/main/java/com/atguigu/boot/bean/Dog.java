package com.atguigu.boot.bean;

import org.springframework.stereotype.Component;

/**
 * @author lfy
 * @Description
 * @create 2023-03-28 17:00
 */
//@Component
public class Dog {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
