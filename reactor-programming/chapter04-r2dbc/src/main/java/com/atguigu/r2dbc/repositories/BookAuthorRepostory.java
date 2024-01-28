package com.atguigu.r2dbc.repositories;

import com.atguigu.r2dbc.entity.TBook;
import com.atguigu.r2dbc.entity.TBookAuthor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 21:54
 */
@Repository
public interface BookAuthorRepostory extends R2dbcRepository<TBookAuthor,Long> {





    // 1-1关联关系； 查出这本图书以及它的作者
    @Query("select b.*,t.name as name from t_book b" +
            " LEFT JOIN t_author t on b.author_id = t.id " +
            " WHERE b.id = :bookId")
    Mono<TBookAuthor> hahaBook(@Param("bookId")Long bookId);


}
