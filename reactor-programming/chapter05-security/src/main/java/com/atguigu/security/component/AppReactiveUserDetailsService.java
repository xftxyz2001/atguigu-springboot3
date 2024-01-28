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
