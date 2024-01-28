package com.atguigu.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lfy
 * @Description
 * @create 2023-12-24 22:22
 */
@SpringBootTest
public class PasswordTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void test(){

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
