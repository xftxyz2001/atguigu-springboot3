package com.atguigu.web.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author lfy
 * @Description
 * @create 2023-04-18 17:21
 */
@Service
public class AService {
    public void a() {
        //当前请求的路径
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


        //任意位置随时通过 RequestContextHolder 获取到当前请求和响应的信息
        HttpServletResponse response = attributes.getResponse();
        HttpServletRequest request = attributes.getRequest();


        String requestURI = request.getRequestURI();
    }
}
