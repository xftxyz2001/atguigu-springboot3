package com.atguigu.r2dbc.config;

import com.atguigu.r2dbc.config.converter.BookConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 21:37
 *
 * 1、写 AuthorRepositories 接口
 */
@EnableR2dbcRepositories //开启 R2dbc 仓库功能；jpa
@Configuration
public class R2DbcConfiguration {


    @Bean //替换容器中原来的
    @ConditionalOnMissingBean
    public R2dbcCustomConversions conversions(){

        //把我们的转换器加入进去； 效果新增了我们的 Converter
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE,new BookConverter());
    }
}
