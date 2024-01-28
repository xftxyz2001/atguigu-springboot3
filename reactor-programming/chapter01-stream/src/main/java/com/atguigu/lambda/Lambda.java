package com.atguigu.lambda;

import java.util.*;
import java.util.function.*;

/**
 * @author lfy
 * @Description
 * @create 2023-11-16 20:07
 */

//函数式接口；只要是函数式接口就可以用Lambda表达式简化
//函数式接口： 接口中有且只有一个未实现的方法，这个接口就叫函数式接口


interface MyInterface {
    int sum(int i, int j);
}

interface MyHaha {
    int haha();

    default int heihei() {
        return 2;
    }

    ; //默认实现
}

interface My666 {
    void aaa(int i,int j,int k);
}


@FunctionalInterface //检查注解，帮我们快速检查我们写的接口是否函数式接口
interface MyHehe {
    int hehe(int i);


}

//1、自己写实现类
class MyInterfaceImpl implements MyInterface {
    @Override
    public int sum(int i, int j) {
        return i + j;
    }
}


public class Lambda {

    public static void main(String[] args) {
        //声明一个函数
        BiConsumer<String,String> consumer = (a,b)->{
            System.out.println("哈哈："+a+"；呵呵："+b);
        };
        consumer.accept("1","2");



        //声明一个函数
        Function<String,Integer> function = (String x) -> Integer.parseInt(x);
        System.out.println(function.apply("2"));


        Supplier<String> supplier = ()-> UUID.randomUUID().toString();
        String s = supplier.get();
        System.out.println(s);


        BiFunction<String,Integer,Long> biFunction = (a,b)-> 888L;

        Predicate<Integer> even = (t)-> t%2 ==0;

//        even.test()//正向判断
//        even.negate().test(2) //反向判断
        System.out.println(even.negate().test(2));


    }


    public static void bbbbb(String[] args) {
        var names = new ArrayList<String>();

        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");


        //比较器
//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        });


        //直接写函数式接口就方便   (o1,o2)->o1.compareTo(o2)
//        Collections.sort(names,(o1,o2)->o1.compareTo(o2));
        System.out.println(names);


        // 类::方法； 引用类中的实例方法； 忽略lambda的完整写法
        Collections.sort(names,String::compareTo);
        System.out.println(names);




        new  Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("哈哈啊");
                    }
                }
        ).start();

        Runnable runnable = () -> System.out.println("aaa");

        new Thread(runnable).start();



        //最佳实战：
        //1、以后调用某个方法传入参数，这个参数实例是一个接口对象，且只定义了一个方法，就直接用lambda简化写法


    }


    /**
     * lambda简化函数式接口实例创建
     *
     * @param args
     */
    public static void aaaa(String[] args) {

        //1、自己创建实现类对象
        MyInterface myInterface = new MyInterfaceImpl();
        System.out.println(myInterface.sum(1, 2));

        //2、创建匿名实现类
        MyInterface myInterface1 = new MyInterface() {
            @Override
            public int sum(int i, int j) {
                return i * i + j * j;
            }
        };
//        System.out.println(myInterface1.sum(2, 3));
        //冗余写法

        //3、lambda表达式:语法糖  参数列表  + 箭头 + 方法体
        MyInterface myInterface2 = (x, y) -> {
            return x * x + y * y;
        };
        System.out.println(myInterface2.sum(2, 3));


        //参数位置最少情况
        MyHaha myHaha = () -> {
            return 1;
        };

        MyHehe myHehe = y -> {
            return y * y;
        };


        MyHehe hehe2 = y -> y - 1;

        //完整写法如上：
        //简化写法：
        //1)、参数类型可以不写，只写(参数名)，参数变量名随意定义;
        //    参数表最少可以只有一个 ()，或者只有一个参数名；
        //2、方法体如果只有一句话，{} 可以省略


        MyHehe hehe3 = y -> y + 1;
        System.out.println(hehe3.hehe(7));
        //以上Lambda表达式简化了实例的创建。


        //总结：
        // 1、Lambda表达式： (参数表) -> {方法体}
        // 2、分辨出你的接口是否函数式接口。 函数式接口就可以lambda简化


    }


}
