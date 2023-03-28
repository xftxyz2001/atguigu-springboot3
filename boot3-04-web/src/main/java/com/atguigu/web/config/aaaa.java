package com.atguigu.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

/**
 * @author lfy
 * @Description
 * @create 2023-04-18 17:17
 */
@Component("Aaaa")
public class aaaa implements View {
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("aaaa");
    }
}
