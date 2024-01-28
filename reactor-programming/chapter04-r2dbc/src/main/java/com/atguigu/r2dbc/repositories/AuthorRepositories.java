package com.atguigu.r2dbc.repositories;

import com.atguigu.r2dbc.entity.TAuthor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 21:38
 */
@Repository
public interface AuthorRepositories extends R2dbcRepository<TAuthor,Long> {

    //默认继承了一堆CRUD方法； 像mybatis-plus

    //QBC： Query By Criteria
    //QBE： Query By Example

    //成为一个起名工程师  where id In () and name like ?
    //仅限单表复杂条件查询
    Flux<TAuthor> findAllByIdInAndNameLike(Collection<Long> id, String name);

    //多表复杂查询

    @Query("select * from t_author") //自定义query注解，指定sql语句
    Flux<TAuthor> findHaha();


    // 1-1：关联
    // 1-N：关联
    //场景：
    // 1、一个图书有唯一作者； 1-1
    // 2、一个作者可以有很多图书： 1-N



}
