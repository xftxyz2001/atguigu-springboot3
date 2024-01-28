package com.atguigu.r2dbc.controller;

import com.atguigu.r2dbc.entity.TAuthor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 20:58
 */

@RestController
public class AuthorController {


    @GetMapping("/author")
    public Flux<TAuthor> getAllAuthor(){


        return null;
    }
}
