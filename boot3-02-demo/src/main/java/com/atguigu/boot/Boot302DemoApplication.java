package com.atguigu.boot;

//import com.alibaba.druid.FastsqlException;
import com.atguigu.boot.bean.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot302DemoApplication {

    public static void main(String[] args) {
       var ioc = SpringApplication.run(Boot302DemoApplication.class, args);

//        for (String name : ioc.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//        String[] forType = ioc.getBeanNamesForType(FastsqlException.class);
//        for (String s : forType) {
//            System.out.println(s);
//        }
//
//        Object userHaha1 = ioc.getBean("userHaha");
//        Object userHaha2 = ioc.getBean("userHaha");
//        System.out.println(userHaha2 == userHaha1);

        for (String s : ioc.getBeanNamesForType(Cat.class)) {
            System.out.println("cat: "+s);
        }

        for (String s : ioc.getBeanNamesForType(Dog.class)) {
            System.out.println("dog: "+s);
        }

        for (String s : ioc.getBeanNamesForType(User.class)) {
            System.out.println("user: "+s);
        }


        Pig pig = ioc.getBean(Pig.class);
        System.out.println("pig: "+pig);


        Sheep sheep = ioc.getBean(Sheep.class);
        System.out.println("sheep: "+sheep);

    }

}
