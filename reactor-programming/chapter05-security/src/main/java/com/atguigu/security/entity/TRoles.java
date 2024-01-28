package com.atguigu.security.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "t_roles")
public class TRoles {
    @Id
    private Long id;
    private String name;
    private String value;
    private Instant createTime;
    private Instant updateTime;

}
