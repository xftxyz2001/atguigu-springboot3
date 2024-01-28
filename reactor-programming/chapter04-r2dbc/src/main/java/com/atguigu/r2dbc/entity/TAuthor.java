package com.atguigu.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 20:35
 */



@Table("t_author")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TAuthor {

    @Id
    private Long id;
    private String name;

    //1-N如何封装
    @Transient //临时字段，并不是数据库表中的一个字段
//    @Field(exist=false)
    private List<TBook> books;
}
