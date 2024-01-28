package com.atguigu.lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author lfy
 * @Description
 * @create 2023-11-16 20:53
 */
public class FunctionDemo {

    public static void main(String[] args) {
        //1、定义数据提供者函数
        Supplier<String> supplier = () -> "47";
        //2、断言：验证是否一个数字
        Predicate<String> isNumber = str -> str.matches("-?\\d+(\\.\\d+)?");
        //3、转换器：把字符串变成数字  类::实例方法（静态方法）
        Function<String, Integer> change = Integer::parseInt;

        //4、消费者：打印数字
        Consumer<Integer> consumer = integer -> {
            if (integer % 2 == 0) {
                System.out.println("偶数：" + integer);
            } else {
                System.out.println("奇数：" + integer);
            }
        };


        //串在一起，实现判断42这个字符串是奇数还是偶数

        mymethod(supplier, isNumber, change, consumer);


//        System.out.println(supplier.get());
//        System.out.println(isNumber.test("777a"));


        mymethod(() -> "777a",
                str -> str.matches("-?\\d+(\\.\\d+)?"),
                Integer::parseInt,
                System.out::println);
    }

    private static void mymethod(Supplier<String> supplier,
                                 Predicate<String> isNumber,
                                 Function<String, Integer> change,
                                 Consumer<Integer> consumer) {
        if (isNumber.test(supplier.get())) {
            //说明是一个数字
            consumer.accept(change.apply(supplier.get()));
        } else {
            //说明不是一个数字
            System.out.println("非法的数字");
        }
    }
}
