package com.atguigu.boot;

import com.atguigu.boot.bean.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lfy
 * @Description 启动SpringBoot项目的主入口程序
 * @create 2023-03-27 18:25
 */

//主程序：com.atguigu.boot
//@SpringBootApplication(scanBasePackages = "com.atguigu")
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan("com.atguigu")
@SpringBootApplication //这是一个SpringBoot应用
public class MainApplication {


    public static void main(String[] args) {

        //java10： 局部变量类型的自动推断
        var ioc = SpringApplication.run(MainApplication.class, args);

        //1、获取容器中所有组件的名字
        String[] names = ioc.getBeanDefinitionNames();
        //2、挨个遍历：
        // dispatcherServlet、beanNameViewResolver、characterEncodingFilter、multipartResolver
        // SpringBoot把以前配置的核心组件现在都给我们自动配置好了。
        for (String name : names) {
            System.out.println(name);
        }


        Person person = ioc.getBean(Person.class);
        System.out.println("person："+person);
        System.out.println("==== 用|表示大文本，会保留格式");
        String s = person.getChild().getText().get(2);
        System.out.println(s);
        System.out.println("==== 用>表示大文本，会压缩换行变成 空格");
        var ss = person.getChild().getText().get(3);
        System.out.println(ss);

        System.out.println("==== 用|表示大文本，会压缩换行变成 空格");
        var ss2 = person.getChild().getText().get(4);
        System.out.println(ss2);

    }
}
