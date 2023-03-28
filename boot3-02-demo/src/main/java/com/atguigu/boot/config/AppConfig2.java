package com.atguigu.boot.config;

//import com.alibaba.druid.FastsqlException;
import com.atguigu.boot.bean.Cat;
import com.atguigu.boot.bean.Dog;
import com.atguigu.boot.bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;

/**
 * @author lfy
 * @Description
 * @create 2023-03-28 18:23
 */
@ConditionalOnMissingClass(value="com.alibaba.druid.FastsqlException") //放在类级别，如果注解判断生效，则整个配置类才生效
@SpringBootConfiguration
public class AppConfig2 {

    @ConditionalOnClass(name="com.alibaba.druid.FastsqlException") //放在方法级别，单独对这个方法进行注解判断。
    @Bean
    public Cat cat01(){
        return new Cat();
    }


    @Bean
    public Dog dog01(){
        return new Dog();
    }

    @ConditionalOnBean(value = Dog.class)
    @Bean
    public User zhangsan(){
        return new User();
    }


    @ConditionalOnMissingBean(value = Dog.class)
    @Bean
    public User lisi(){
        return new User();
    }
}
