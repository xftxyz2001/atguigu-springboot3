Web、网络、IO（存储）、中间件（Redis、MySQL）
应用开发：

- 网络
- **存储**：MySQL、Redis
- **Web**：Webflux
- 前端； 后端：Controller -- Service -- Dao（r2dbc；mysql）


数据库：

- **导入驱动**； 以前：JDBC（jdbc、各大驱动mysql-connector）； 现在：r2dbc（[r2dbc-spi](https://github.com/r2dbc/r2dbc-spi)、各大驱动 r2dbc-mysql）
- **驱动**：
   - 获取连接
   - 发送SQL、执行
   - 封装数据库返回结果

今日任务：

- r2dbc原生API：https://r2dbc.io
- boot整合spring data r2dbc：spring-boot-starter-data-r2dbc
- 三大组件：R2dbcRepository、R2dbcEntityTemplate 、DatabaseClient
- RBAC权限模型导入，基础CRUD练习；SQL文件在附录
- 1-1，1-N 关系处理；
- 扩展：导入接口文档进行测试： 访问 项目/doc.html
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.0.2</version>
</dependency>
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-ui</artifactId>
    <version>4.0.0</version>
</dependency>
```


# 1、R2dbc
> 用法：
> 1、导入驱动: 导入连接池（[r2dbc-pool](https://github.com/r2dbc/r2dbc-pool)）、导入驱动（[r2dbc-mysql](https://github.com/asyncer-io/r2dbc-mysql) ）
> 2、使用驱动提供的API操作

```xml
        <dependency>
            <groupId>io.asyncer</groupId>
            <artifactId>r2dbc-mysql</artifactId>
            <version>1.0.5</version>
        </dependency>
```



```java

        //0、MySQL配置
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host("localhost")
                .port(3306)
                .username("root")
                .password("123456")
                .database("test")
                .build();

        //1、获取连接工厂
        MySqlConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration);


        //2、获取到连接，发送sql

        // JDBC： Statement： 封装sql的
        //3、数据发布者
        Mono.from(connectionFactory.create())
                .flatMapMany(connection ->
                        connection
                                .createStatement("select * from t_author where id=?id and name=?name")
                                .bind("id",1L) //具名参数
                                .bind("name","张三")
                                .execute()
                ).flatMap(result -> {
                    return result.map(readable -> {
                        Long id = readable.get("id", Long.class);
                        String name = readable.get("name", String.class);
                        return new TAuthor(id, name);
                    });
                })
                .subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor))
        ;
```




# 2、Spring Data R2DBC
> 提升生产力方式的 响应式数据库操作


## 0、整合
1、导入依赖
```xml
        <!-- https://mvnrepository.com/artifact/io.asyncer/r2dbc-mysql -->
        <dependency>
            <groupId>io.asyncer</groupId>
            <artifactId>r2dbc-mysql</artifactId>
            <version>1.0.5</version>
        </dependency>
        <!--        响应式 Spring Data R2dbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-r2dbc</artifactId>
        </dependency>
```


2、编写配置
```yaml
spring:
  r2dbc:
    password: 123456
    username: root
    url: r2dbc:mysql://localhost:3306/test
    name: test
```

## 1、声明式接口：R2dbcRepository
### Repository接口
```java
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
```

### 自定义Converter
```java
package com.atguigu.r2dbc.config.converter;

import com.atguigu.r2dbc.entity.TAuthor;
import com.atguigu.r2dbc.entity.TBook;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;

/**
 * @author lfy
 * @Description
 * @create 2023-12-23 22:04
 *
 * 告诉Spring Data 怎么封装Book对象
 */
@ReadingConverter //读取数据库数据的时候,把row转成 TBook
public class BookConverter implements Converter<Row, TBook> {
    @Override
    public TBook convert(Row source) {
        if(source == null) return null;
        //自定义结果集的封装
        TBook tBook = new TBook();

        tBook.setId(source.get("id", Long.class));
        tBook.setTitle(source.get("title", String.class));

        Long author_id = source.get("author_id", Long.class);
        tBook.setAuthorId(author_id);
        //        tBook.setPublishTime(source.get("publish_time", Instant.class));


        TAuthor tAuthor = new TAuthor();
        tAuthor.setId(author_id);
        tAuthor.setName(source.get("name", String.class));

        tBook.setAuthor(tAuthor);

        return null;
    }
}
```



### 配置生效
```java
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
```
## 2、编程式组件

- R2dbcEntityTemplate 
- DatabaseClient


# 3、RBAC-SQL练习
## 1、1-1
> - 自定义 Converter<Row，Bean> 方式

```java
    @Bean
    R2dbcCustomConversions r2dbcCustomConversions(){
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new BookConverter());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }

//1-1： 结合自定义 Converter
bookRepostory.hahaBook(1L)
        .subscribe(tBook -> System.out.println("tBook = " + tBook));
```
> - 编程式封装方式: 使用DatabaseClient

```java
//1-1：第二种方式
databaseClient.sql("select b.*,t.name as name from t_book b " +
                "LEFT JOIN t_author t on b.author_id = t.id " +
                "WHERE b.id = ?")
        .bind(0, 1L)
        .fetch()
        .all()
        .map(row-> {
            String id = row.get("id").toString();
            String title = row.get("title").toString();
            String author_id = row.get("author_id").toString();
            String name = row.get("name").toString();
            TBook tBook = new TBook();

            tBook.setId(Long.parseLong(id));
            tBook.setTitle(title);

            TAuthor tAuthor = new TAuthor();
            tAuthor.setName(name);
            tAuthor.setId(Long.parseLong(author_id));

            tBook.setAuthor(tAuthor);

            return tBook;
        })
        .subscribe(tBook -> System.out.println("tBook = " + tBook));
```

## 2、1-N
> 使用底层API DatabaseClient；

```java
    @Test
    void oneToN() throws IOException {

//        databaseClient.sql("select a.id aid,a.name,b.* from t_author a  " +
//                "left join t_book b on a.id = b.author_id " +
//                "order by a.id")
//                .fetch()
//                .all(row -> {
//
//                })


        // 1~6
        // 1：false 2：false 3:false 4: true 8:true 5:false 6:false 7:false 8:true 9:false 10:false
        // [1,2,3]
        // [4,8]
        // [5,6,7]
        // [8]
        // [9,10]
        // bufferUntilChanged：
        // 如果下一个判定值比起上一个发生了变化就开一个新buffer保存，如果没有变化就保存到原buffer中

//        Flux.just(1,2,3,4,8,5,6,7,8,9,10)
//                .bufferUntilChanged(integer -> integer%4==0 )
//                .subscribe(list-> System.out.println("list = " + list));
        ; //自带分组


        Flux<TAuthor> flux = databaseClient.sql("select a.id aid,a.name,b.* from t_author a  " +
                        "left join t_book b on a.id = b.author_id " +
                        "order by a.id")
                .fetch()
                .all()
                .bufferUntilChanged(rowMap -> Long.parseLong(rowMap.get("aid").toString()))
                .map(list -> {
                    TAuthor tAuthor = new TAuthor();
                    Map<String, Object> map = list.get(0);
                    tAuthor.setId(Long.parseLong(map.get("aid").toString()));
                    tAuthor.setName(map.get("name").toString());


                    //查到的所有图书
                    List<TBook> tBooks = list.stream()
                            .map(ele -> {
                                TBook tBook = new TBook();

                                tBook.setId(Long.parseLong(ele.get("id").toString()));
                                tBook.setAuthorId(Long.parseLong(ele.get("author_id").toString()));
                                tBook.setTitle(ele.get("title").toString());
                                return tBook;
                            })
                            .collect(Collectors.toList());

                    tAuthor.setBooks(tBooks);
                    return tAuthor;
                });//Long 数字缓存 -127 - 127；// 对象比较需要自己写好equals方法



        flux.subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));

        System.in.read();


    }
```




# 4、最佳实践

> 最佳实践：  提升生产效率的做法
> - 1、Spring Data R2DBC，基础的CRUD用 **R2dbcRepository** 提供好了
> - 2、自定义复杂的SQL（**单表**）： **@Query**；
> - 3、**多表查询复杂结果集**： **DatabaseClient** 自定义SQL及结果封装；
>    - **@Query + 自定义 Converter 实现结果封装**
> 
**经验：**
> - **1-1:1-N 关联关系的封装都需要自定义结果集的方式**
>    - **Spring Data R2DBC： **
>       - **自定义Converter指定结果封装**
>       - **DatabaseClient：贴近底层的操作进行封装; 见下面代码**
>    - **MyBatis：  自定义 ResultMap 标签去来封装**

```java
        databaseClient.sql("select b.*,t.name as name from t_book b " +
                        "LEFT JOIN t_author t on b.author_id = t.id " +
                        "WHERE b.id = ?")
                .bind(0, 1L)
                .fetch()
                .all()
                .map(row-> {
                    String id = row.get("id").toString();
                    String title = row.get("title").toString();
                    String author_id = row.get("author_id").toString();
                    String name = row.get("name").toString();
                    TBook tBook = new TBook();

                    tBook.setId(Long.parseLong(id));
                    tBook.setTitle(title);

                    TAuthor tAuthor = new TAuthor();
                    tAuthor.setName(name);
                    tAuthor.setId(Long.parseLong(author_id));

                    tBook.setAuthor(tAuthor);

                    return tBook;
                })
                .subscribe(tBook -> System.out.println("tBook = " + tBook));
```



# 附录

- RBAC SQL文件
```sql
-- 用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`(
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                           `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
                           `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
                           `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
                           `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                           `update_time` datetime(0) NOT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 角色表
DROP TABLE IF EXISTS `t_roles`;
CREATE TABLE `t_roles`(
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
                            `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色的英文名',
                            `create_time` datetime(0) NOT NULL,
                            `update_time` datetime(0) NOT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 权限表（资源表）
DROP TABLE IF EXISTS `t_perm`;
CREATE TABLE `t_perm`(
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限字段',
                               `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源路径',
                               `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源描述',
                               `create_time` datetime(0) NOT NULL,
                               `update_time` datetime(0) NOT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 用户角色关系表
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`(
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `user_id` bigint(20) NOT NULL,
                                `role_id` bigint(20) NOT NULL,
                                `create_time` datetime(0) NOT NULL,
                                `update_time` datetime(0) NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- 角色权限关系表
DROP TABLE IF EXISTS `t_role_perm`;
CREATE TABLE `t_role_perm`(
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `role_id` bigint(20) NOT NULL,
                                    `perm_id` bigint(20) NOT NULL,
                                    `create_time` datetime(0) NOT NULL,
                                    `update_time` datetime(0) NOT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- 图书&作者表
CREATE TABLE `t_book`(
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `title` varchar(255) NOT NULL,
                              `author_id` bigint(20) NOT NULL,
                              `publish_time` datetime(0) NOT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

CREATE TABLE `t_author`(
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) NOT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
```















