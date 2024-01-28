package com.atguigu.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lfy
 * @Description
 * @create 2023-11-16 21:09
 */
public class StreamDemo {

    private static String[] buffer = new String[1];

    public static void main(String[] args) {
        StreamDemo demo = new StreamDemo();
        System.out.println("1111");
        demo.a();


        //b要消费数据
        new Thread(() -> {
            demo.b("aaa"); //b也可以进行失败重试
        }).start();

        System.out.println("2222");


    }


    public void a() {
        String a = "aaaa";
        System.out.println("a做完事....");
        buffer[0] = a; //消息队列

        //引入一个缓存区，引入消息队列，就能实现全系统、全异步、不阻塞、不等待、实时响应
    }


    public void b(String arg) {
        arg = buffer[0];
        try {

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("哈哈：" + arg);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class Person {
        private String name;
        private String gender;
        private Integer age;
    }


    public static void mainaaa(String[] args) {
        List<Person> list = List.of(
                new Person("雷 丰阳", "男", 16),
                new Person("王 五", "女", 20),
                new Person("赵 六", "男", 22),
                new Person("王 七", "女", 33),
                new Person("雷 二", "女", 18));

        //迭代器模式；
        for (Person person : list) {
            //1、迭代速度取决于数据量
            //2、数据还得有容器缓存
        }

        //背压：
        //正压：正向压力：数据的生产者给消费者压力；
//        list.stream()
//                .filter(a->{
//                    System.out.println("aaaa");
//                })


        //数据是自流动的，而不是靠迭代被动流动；
        //推拉模型：
        //推：流模式；上游有数据，自动推给下游；
        //拉：迭代器；自己遍历，自己拉取；
        Map<String, List<Person>> collect =
                list.stream()
                        .parallel()
                        .filter(s -> s.age > 15)
                        .collect(Collectors.groupingBy(t -> t.gender));


        //Flow
        System.out.println(collect);


    }


    public static void mainbbbb(String[] args) {

        List<Person> list = List.of(
                new Person("雷 丰阳", "男", 16),
                new Person("王 五", "女", 20),
                new Person("赵 六", "男", 22),
                new Person("王 七", "女", 33),
                new Person("雷 二", "女", 18));


        //1、挑出 年龄大于 18岁的人  拿到集合流其实就是拿到集合的深拷贝的值，流的所有操作都是流的元素引用
        //filter、map、flatMap； 流里面的每一个元素都完整走一个流水线，才能轮到下一个元素；
        //第一个元素流经所有管道处理后，下一个元素才能继续执行完整管道流程
        //声明式：基于事件机制的回调
        Stream<String> sorted = list.stream()
                .limit(3)
                .filter(person -> { //程序员不自己调用，发生这个事情的时候系统调用
//                    System.out.println("filter："+person.hashCode());
                    return person.age > 18;
                }) //挑出大于18； person流
                .peek(person -> System.out.println("filter peek:" + person))
                .map(person -> {
//                    System.out.println("Person："+person.hashCode());
                    return person.getName();
                })//拿到所有人的姓名
                .peek(s -> System.out.println("map peek:" + s))
                .flatMap(ele -> {
                    String[] s = ele.split(" ");
                    return Arrays.stream(s);
                })
                .distinct()
                .sorted(String::compareTo);


//        long count = sorted.count();

//                .forEach(e->{
//                    System.out.println("元素："+e);
////                    try {
//////                        Thread.sleep(2000);
////                    } catch (InterruptedException ex) {
////                        throw new RuntimeException(ex);
////                    }
//
//                });


        //distinct、sorted、peek、limit、skip、takeWhile


        System.out.println("==================");


        List<Integer> collect = List.of(1, 2, 3, 4, 5, 6)
                .stream()
                .filter(i -> i > 2) //无条件遍历流中的每一个元素
                .collect(Collectors.toList());
        System.out.println(collect);


        List<Integer> collect1 = List.of(1, 2, 3, 4, 5, 6)
                .stream()
                .takeWhile(i -> i < 2) //当满足条件，拿到这个元素，不满足直接结束流操作
                .collect(Collectors.toList());

        System.out.println(collect1);


        //filter、map、flatMap、takeWhile.....


    }


    public static void aaaaa(String[] args) {

        //1、挑出最大偶数
        List<Integer> list = List.of(1, 2, 3, 4, -2, 6, 7, 8, 9);


        //for循环，挨个遍历找到偶数，temp = i; 下次找到的偶数和临时遍历比较
        int max = 0;
        for (Integer integer : list) {
            if (integer % 2 == 0) {
                //是偶数，和max进行比较交换
                max = integer >= max ? integer : max;
            }
        }

        System.out.println("最大偶数：" + max);

        //流特性：
        //1）、流是lazy的，不用，方法就不会被调用

        //2、StreamAPI；
        //1）、把数据封装成流；要到数据流； 集合类.stream
        //2)、定义流式操作
        //3）、获取最终结果
        list.stream()
                .filter(ele -> {
                    System.out.println("正在filter：" + ele);
                    return ele % 2 == 0;
                }) // intermediate operation.中间操作
                .max(Integer::compareTo)
                .ifPresent(System.out::println); // terminal operation. 终止操作 //过滤出我们想要值，如果断言返回true就是我们要的

        //全类名::方法

        //流的三大部部分：
        //1、数据流    2、N个中间操作  3、一个终止操作
        //1、数据流：
        //   1）、创建流
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<Integer> concat = Stream.concat(Stream.of(2, 3, 4), stream);
        Stream<Object> build = Stream.builder().add("11").add("22").build();

        //   2）、从集合容器中获取这个流，List、Set、Map
        List<Integer> integers = List.of(1, 2);
        Stream<Integer> stream1 = integers.stream();

        Set<Integer> integers1 = Set.of(1, 2);
        integers1.stream();

        Map<Object, Object> of = Map.of();
        of.keySet().stream();
        of.values().stream();


        System.out.println("主线程：" + Thread.currentThread());

        //流是并发还是不并发？和for有啥区别？ 流也是用for循环挨个处理； 默认不并发，也可以并发；
        //并发以后，你要自行解决多线程安全问题
//        List aaa = new ();

        List<Object> objectList = Collections.synchronizedList(new ArrayList<>());

        //有状态数据将产生并发安全问题。千万不要这么写？
        //流的所有操作都是无状态；数据状态仅在此函数内有效，不溢出至函数外
        long count = Stream.of(1, 2, 3, 4, 5)
                .parallel() //intermediate operation. 并发流
                .filter(i -> {
//                    objectList.add(i);
                    System.out.println("filter线程：" + Thread.currentThread());
                    System.out.println("正在filter：" + i);
                    return i > 2;
                }) // intermediate operation.
                .count(); // terminal operation.


        System.out.println(count);


    }
}
