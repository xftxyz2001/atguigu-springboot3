package com.atguigu.boot3.features;


import com.atguigu.boot3.features.service.HelloService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;


//测试类也必须在主程序所在的包及其子包
@SpringBootTest //具备测试SpringBoot应用容器中所有组件的功能
class Boot306FeaturesApplicationTests {


    @Autowired //自动注入任意组件即可测试
    HelloService helloService;

    @DisplayName("测试1")
    @Test
    void contextLoads() {
        int sum = helloService.sum(1, 2);
        Assertions.assertEquals(4,sum);

    }

    @ParameterizedTest //参数化测试
    @ValueSource(strings = {"one", "two", "three"})
    @DisplayName("参数化测试1")
    public void parameterizedTest1(String string) {
        System.out.println(string);
        Assertions.assertTrue(StringUtils.isNotBlank(string));
    }

    @DisplayName("😱")
    @Test
    void test01(){
        System.out.println("aaaa");
    }

    @BeforeAll  //所有测试方法运行之前先运行这个 ： 只打印一次
    static void initAll() {
        System.out.println("hello");
    }

    @BeforeEach //每个测试方法运行之前先运行这个 ： 每个方法运行打印一次
    void init() {
        System.out.println("world");
    }


    @ParameterizedTest
    @MethodSource("method")    //指定方法名,返回值就是测试用的参数
    @DisplayName("方法来源参数")
    public void testWithExplicitLocalMethodSource(String name) {
        System.out.println(name);
        Assertions.assertNotNull(name);
    }

    //返回Stream即可
    static Stream<String> method() {
        return Stream.of("apple", "banana");
    }

}
