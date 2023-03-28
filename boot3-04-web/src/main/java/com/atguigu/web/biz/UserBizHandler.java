package com.atguigu.web.biz;

import com.atguigu.web.bean.Person;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author lfy
 * @Description 专门处理User有关的业务
 * @create 2023-04-18 21:55
 */
@Slf4j
@Service
public class UserBizHandler {

    /**
     * 查询指定id的用户
     * @param request
     * @return
     */
    public ServerResponse getUser(ServerRequest request) throws Exception{
        String id = request.pathVariable("id");
        log.info("查询 【{}】 用户信息，数据库正在检索",id);
        //业务处理
        Person person = new Person(1L,"哈哈","aa@qq.com",18,"admin");
        //构造响应
        return ServerResponse
                .ok()
                .body(person);
    }


    /**
     * 获取所有用户
     * @param request
     * @return
     * @throws Exception
     */
    public ServerResponse getUsers(ServerRequest request) throws Exception{
        log.info("查询所有用户信息完成");
        //业务处理
        List<Person> list = Arrays.asList(new Person(1L, "哈哈", "aa@qq.com", 18, "admin"),
                new Person(2L, "哈哈2", "aa2@qq.com", 12, "admin2"));

        //构造响应
        return ServerResponse
                .ok()
                .body(list); //凡是body中的对象，就是以前@ResponseBody原理。利用HttpMessageConverter 写出为json
    }


    /**
     * 保存用户
     * @param request
     * @return
     */
    public ServerResponse saveUser(ServerRequest request) throws ServletException, IOException {
        //提取请求体
        Person body = request.body(Person.class);
        log.info("保存用户信息：{}",body);
        return ServerResponse.ok().build();
    }

    /**
     * 更新用户
     * @param request
     * @return
     */
    public ServerResponse updateUser(ServerRequest request) throws ServletException, IOException {
        Person body = request.body(Person.class);
        log.info("保存用户信息更新: {}",body);
        return ServerResponse.ok().build();
    }

    /**
     * 删除用户
     * @param request
     * @return
     */
    public ServerResponse deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        log.info("删除【{}】用户信息",id);
        return ServerResponse.ok().build();
    }
}
