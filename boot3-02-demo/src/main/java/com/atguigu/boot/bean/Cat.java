package com.atguigu.boot.bean;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author lfy
 * @Description
 * @create 2023-03-28 17:00
 */
//@Component
public class Cat {
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
