package com.atguigu.security.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "t_role_perm")
public class TRolePerm {
    @Id
    private Long id;
    private Long roleId;
    private Long permId;
    private Instant createTime;
    private Instant updateTime;


}
