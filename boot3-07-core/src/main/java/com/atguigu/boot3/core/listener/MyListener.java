package com.atguigu.boot3.core.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author lfy
 * @Description
 * @create 2023-04-24 16:35
 */
public class MyListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("=====事件====到达===="+event);
    }
}
