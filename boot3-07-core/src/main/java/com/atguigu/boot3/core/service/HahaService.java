package com.atguigu.boot3.core.service;

import com.atguigu.boot3.core.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author lfy
 * @Description
 * @create 2023-04-24 19:04
 */
@Service
public class HahaService {

    @EventListener
    public void onEvent(LoginSuccessEvent event){
        System.out.println("=== HahaService === 感知到事件"+event);
        //调用业务
    }
}
