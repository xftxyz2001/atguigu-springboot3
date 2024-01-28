package com.atguigu.security.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "t_user")
public class TUser {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;

    private Instant createTime;

    private Instant updateTime;


}
