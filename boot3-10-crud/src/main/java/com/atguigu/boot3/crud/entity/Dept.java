package com.atguigu.boot3.crud.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 16:42
 */
@Schema(title = "部门信息")
@Data
public class Dept {

    @Schema(title = "部门id")
    private Long id;
    @Schema(title = "部门名字")
    private String deptName;
}
