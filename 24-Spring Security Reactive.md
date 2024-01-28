> 目标：
> SpringBoot +  Webflux + Spring Data R2DBC + Spring Security


今日任务：

- RBAC权限模型
- WebFlux配置：@EnableWebFluxSecurity、@EnableReactiveMethodSecurity
- SecurityFilterChain 组件
- AuthenticationManager  组件
- UserDetailsService  组件
- 基于注解的方法级别授权


# 1、整合
```xml
    <dependencies>
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

        <!--        响应式Web  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
```



# 2、开发
## 1、应用安全

- **防止攻击**：
   - DDos、CSRF、XSS、SQL注入...
- **控制权限**
   - 登录的用户能干什么。
   - 用户登录系统以后要控制住用户的所有行为，防止越权；
- 传输加密
   - https
   - X509
- 认证：
   - OAuth2.0
   - JWT
## 2、RBAC权限模型
Role Based Access Controll： 基于角色的访问控制


一个网站有很多**用户**： zhangsan
每个用户可以有很多**角色**：
一个角色可以关联很多**权限**：
一个人到底能干什么？
> 权限控制：
> - 找到这个人，看他有哪些角色，每个角色能拥有哪些**权限**。  这个人就拥有一堆的 **角色** 或者 **权限**
> - 这个人执行方法的时候，我们给方法规定好权限，由权限框架负责判断，这个人是否有指定的权限


所有权限框架：

- 让用户登录进来：  **认证（authenticate）**：用账号密码、各种其他方式，先让用户进来
- 查询用户拥有的所有角色和权限： **授权（authorize）**： 每个方法执行的时候，匹配角色或者权限来判定用户是否可以执行这个方法



导入Spring Security：默认效果


# 3、认证
> 登录行为


## 1、静态资源放行
## 2、其他请求需要登录

```java
package com.atguigu.security.config;

import com.atguigu.security.component.AppReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 21:39
 */
@Configuration
@EnableReactiveMethodSecurity //开启响应式 的 基于方法级别的权限控制
public class AppSecurityConfiguration {


    @Autowired
    ReactiveUserDetailsService appReactiveUserDetailsService;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //1、定义哪些请求需要认证，哪些不需要
        http.authorizeExchange(authorize -> {
            //1.1、允许所有人都访问静态资源；
            authorize.matchers(PathRequest.toStaticResources()
                    .atCommonLocations()).permitAll();


            //1.2、剩下的所有请求都需要认证（登录）
            authorize.anyExchange().authenticated();
        });

        //2、开启默认的表单登录
        http.formLogin(formLoginSpec -> {
//            formLoginSpec.loginPage("/haha");
        });

        //3、安全控制:
        http.csrf(csrfSpec -> {
            csrfSpec.disable();
        });

        // 目前认证： 用户名 是 user  密码是默认生成。
        // 期望认证： 去数据库查用户名和密码

        //4、配置 认证规则： 如何去数据库中查询到用户;
        // Sprinbg Security 底层使用 ReactiveAuthenticationManager 去查询用户信息
        // ReactiveAuthenticationManager 有一个实现是
        //   UserDetailsRepositoryReactiveAuthenticationManager： 用户信息去数据库中查
        //   UDRespAM 需要  ReactiveUserDetailsService：
        // 我们只需要自己写一个 ReactiveUserDetailsService： 响应式的用户详情查询服务
        http.authenticationManager(
                new UserDetailsRepositoryReactiveAuthenticationManager(
                        appReactiveUserDetailsService)
        );



//        http.addFilterAt()





        //构建出安全配置
        return http.build();
    }


    @Primary
    @Bean
    PasswordEncoder passwordEncoder(){

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}

```


![image.png](https://cdn.nlark.com/yuque/0/2023/png/1613913/1703426405379-3b1b52b2-b85b-4749-849a-dc1f67898118.png#averageHue=%23f3f2f2&clientId=u1d93a98e-60b2-4&from=paste&height=216&id=u5431abfe&originHeight=270&originWidth=474&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=9274&status=done&style=none&taskId=u9277b29e-cf9e-4b72-9df2-036f3c2cc00&title=&width=379.2)
这个界面点击登录，最终Spring Security 框架会使用 ReactiveUserDetailsService 组件，按照 表单提交的用户名 去**数据库查询**这个**用户详情**（**基本信息**[账号、密码]，**角色**，**权限**）；
把数据库中返回的 **用户详情** 中的密码 和 表单提交的密码进行比对。比对成功则登录成功；


```java
package com.atguigu.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 21:57
 */
@Component  // 来定义如何去数据库中按照用户名查用户
public class AppReactiveUserDetailsService implements ReactiveUserDetailsService {


    @Autowired
    DatabaseClient databaseClient;

    // 自定义如何按照用户名去数据库查询用户信息

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Mono<UserDetails> findByUsername(String username) {


//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //从数据库查询用户、角色、权限所有数据的逻辑
        Mono<UserDetails> userDetailsMono = databaseClient.sql("select u.*,r.id rid,r.name,r.value,pm.id pid,pm.value pvalue,pm.description " +
                        "from t_user u " +
                        "left join t_user_role ur on ur.user_id=u.id " +
                        "left join t_roles r on r.id = ur.role_id " +
                        "left join t_role_perm rp on rp.role_id=r.id " +
                        "left join t_perm pm on rp.perm_id=pm.id " +
                        "where u.username = ? limit 1")
                .bind(0, username)
                .fetch()
                .one()// all()
                .map(map -> {
                    UserDetails details = User.builder()
                            .username(username)
                            .password(map.get("password").toString())
                            //自动调用密码加密器把前端传来的明文 encode
//                            .passwordEncoder(str-> passwordEncoder.encode(str)) //为啥？？？
                            //权限
//                            .authorities(new SimpleGrantedAuthority("ROLE_delete")) //默认不成功
                            .roles("admin", "sale","haha","delete") //ROLE成功
                            .build();

                    //角色和权限都被封装成 SimpleGrantedAuthority
                    // 角色有 ROLE_ 前缀， 权限没有
                    // hasRole：hasAuthority
                    return details;
                });

        return userDetailsMono;
    }
}

```

# 4、授权
@EnableReactiveMethodSecurity

```java
package com.atguigu.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 21:31
 */
@RestController
public class HelloController {


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/hello")
    public Mono<String> hello(){

        return Mono.just("hello world!");
    }


    // 角色 haha： ROLE_haha：角色
    // 没有ROLE 前缀是权限

    //复杂的SpEL表达式
    @PreAuthorize("hasRole('delete')")
    @GetMapping("/world")
    public Mono<String> world(){
        return Mono.just("world!!!");
    }
}

```




官方实例：
[https://github.com/spring-projects/spring-security-samples/tree/main](https://github.com/spring-projects/spring-security-samples/tree/main)


配置是： SecurityWebFilterChain
```java
package com.atguigu.security.config;

import com.atguigu.security.component.AppReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 21:39
 */
@Configuration
@EnableReactiveMethodSecurity //开启响应式 的 基于方法级别的权限控制
public class AppSecurityConfiguration {


    @Autowired
    ReactiveUserDetailsService appReactiveUserDetailsService;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //1、定义哪些请求需要认证，哪些不需要
        http.authorizeExchange(authorize -> {
            //1.1、允许所有人都访问静态资源；
            authorize.matchers(PathRequest.toStaticResources()
                    .atCommonLocations()).permitAll();


            //1.2、剩下的所有请求都需要认证（登录）
            authorize.anyExchange().authenticated();
        });

        //2、开启默认的表单登录
        http.formLogin(formLoginSpec -> {
//            formLoginSpec.loginPage("/haha");
        });

        //3、安全控制:
        http.csrf(csrfSpec -> {
            csrfSpec.disable();
        });

        // 目前认证： 用户名 是 user  密码是默认生成。
        // 期望认证： 去数据库查用户名和密码

        //4、配置 认证规则： 如何去数据库中查询到用户;
        // Sprinbg Security 底层使用 ReactiveAuthenticationManager 去查询用户信息
        // ReactiveAuthenticationManager 有一个实现是
        //   UserDetailsRepositoryReactiveAuthenticationManager： 用户信息去数据库中查
        //   UDRespAM 需要  ReactiveUserDetailsService：
        // 我们只需要自己写一个 ReactiveUserDetailsService： 响应式的用户详情查询服务
        http.authenticationManager(
                new UserDetailsRepositoryReactiveAuthenticationManager(
                        appReactiveUserDetailsService)
        );







        //构建出安全配置
        return http.build();
    }


    @Primary
    @Bean
    PasswordEncoder passwordEncoder(){

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
```


