package com.atguigu.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @author lfy
 * @Description
 * @create 2023-11-30 20:07
 */
public class APITest {


    @Test
    void next(){

        Integer block = Flux.just(1, 2, 3)
                .next()
                .block();
        System.out.println(block);
    }

    //Context-API： https://projectreactor.io/docs/core/release/reference/#context
    @Test //ThreadLocal在响应式编程中无法使用。
    //响应式中，数据流期间共享数据，Context API: Context：读写 ContextView：只读；
    void threadlocal(){

        //支持Context的中间操作
        Flux.just(1,2,3)
                .transformDeferredContextual((flux,context)->{
                    System.out.println("flux = " + flux);
                    System.out.println("context = " + context);
                    return flux.map(i->i+"==>"+context.get("prefix"));
                })
                //上游能拿到下游的最近一次数据
                .contextWrite(Context.of("prefix","哈哈"))
                //ThreadLocal共享了数据，上游的所有人能看到; Context由下游传播给上游
                .subscribe(v-> System.out.println("v = " + v));







        //以前 命令式编程

//        controller -- service -- dao
        //响应式编程 dao(10：数据源) --> service(10) --> controller(10); 从下游反向传播




    }


    @Test
    void paralleFlux() throws IOException {
        // 百万数据，8个线程，每个线程处理100，进行分批处理一直处理结束

        Flux.range(1,1000000)
                .buffer(100)
                .parallel(8)
                .runOn(Schedulers.newParallel("yy"))
                .log()
                .flatMap(list->Flux.fromIterable(list))
                .collectSortedList(Integer::compareTo)
                .subscribe(v-> System.out.println("v = " + v));


        System.in.read();
    }


    @Test
     void block(){
//
//        Integer integer = Flux.just(1, 2, 4)
//                .map(i -> i + 10)
//                .blockLast();
//        System.out.println(integer);


        List<Integer> integers = Flux.just(1, 2, 4)
                .map(i -> i + 10)
                .collectList()
                .block(); // 也是一种订阅者； BlockingMonoSubscriber

        System.out.println("integers = " + integers);
    }


    @Test
    void sinks() throws InterruptedException, IOException {

//       Flux.create(fluxSink -> {
//           fluxSink.next("111")
//       })

//        Sinks.many(); //发送Flux数据。
//        Sinks.one(); //发送Mono数据


        // Sinks： 接受器，数据管道，所有数据顺着这个管道往下走的

        //Sinks.many().unicast(); //单播：  这个管道只能绑定单个订阅者（消费者）
        //Sinks.many().multicast();//多播： 这个管道能绑定多个订阅者
        //Sinks.many().replay();//重放： 这个管道能重放元素。 是否给后来的订阅者把之前的元素依然发给它；

        // 从头消费还是从订阅的那一刻消费；

//        Sinks.Many<Object> many = Sinks.many()
//                .multicast() //多播
//                .onBackpressureBuffer(); //背压队列

        //默认订阅者，从订阅的那一刻开始接元素

        //发布者数据重放； 底层利用队列进行缓存之前数据
//        Sinks.Many<Object> many = Sinks.many().replay().limit(3);
//
//        new Thread(()->{
//            for (int i = 0; i < 10; i++) {
//                many.tryEmitNext("a-"+i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
//
//
//
//        //订阅
//        many.asFlux().subscribe(v-> System.out.println("v1 = " + v));
//
//        new Thread(()->{
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            many.asFlux().subscribe(v-> System.out.println("v2 = " + v));
//        }).start();


        Flux<Integer> cache = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1)) //不调缓存默认就是缓存所有
                .cache(1);   //缓存两个元素； 默认全部缓存


        cache.subscribe();//缓存元素;



        // 最定义订阅者
        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cache.subscribe(v-> System.out.println("v = " + v));
        }).start();





        System.in.read();


    }



    @Test
    void retryAndTimeout() throws IOException {

        Flux.just(1)
                .delayElements(Duration.ofSeconds(3))
                .log()
                .timeout(Duration.ofSeconds(2))
                .retry(2) // 把流从头到尾重新请求一次
                .onErrorReturn(2)
                .map(i-> i+"haha")
                .subscribe(v-> System.out.println("v = " + v));


        System.in.read();

    }


    void createOrder() {
        //1、校验订单；价格有问题；
        // 只需要敲正确的业务代码，所有的业务异常，全部抛出我们自定义异常，由全局异常处理器进行统一处理
//        throw new GmallException(ResultCodeEnum.ORDER_PRICE_INVAILID);
    }


    // 默认：错误是一种中断行为；
    // subscribe: 消费者可以感知 正常元素try 与 流发生的错误catch ;
    // 更多的错误处理：
    // java  错误处理
    @Test
    void error() throws IOException {
//        Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorReturn(NullPointerException.class,"哈哈-6666")
//                .subscribe(v-> System.out.println("v = " + v),
//                        err -> System.out.println("err = " + err),
//                        ()-> System.out.println("流结束")); // error handling example

//
//        Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorResume(err -> hahaha(err))
//                .subscribe(v -> System.out.println("v = " + v),
//                        err -> System.out.println("err = " + err),
//                        () -> System.out.println("流结束"));


//                Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorResume(err -> Flux.error(new BusinessException(err.getMessage()+"：炸了")))


//        Flux.just(1, 2, 0, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .onErrorMap(err-> new BusinessException(err.getMessage()+": 又炸了..."))
//                .subscribe(v -> System.out.println("v = " + v),
//                        err -> System.out.println("err = " + err),
//                        () -> System.out.println("流结束"));

//        Flux.just(1, 2, 3, 4)
//                .map(i -> "100 / " + i + " = " + (100 / i))
//                .doOnError(err -> {
//                    System.out.println("err已被记录 = " + err);
//                })
//                .doFinally(signalType -> {
//                    System.out.println("流信号："+signalType);
//                })
//                .subscribe(v -> System.out.println("v = " + v),
//                        err -> System.out.println("err = " + err),
//                        () -> System.out.println("流结束"));




//        Flux.just(1,2,3,0,5)
//                .map(i->10/i)
//                .onErrorContinue((err,val)->{
//                    System.out.println("err = " + err);
//                    System.out.println("val = " + val);
//                    System.out.println("发现"+val+"有问题了，继续执行其他的，我会记录这个问题");
//                }) //发生
//                .subscribe(v-> System.out.println("v = " + v),
//                        err-> System.out.println("err = " + err));

        Flux<Long> map = Flux.interval(Duration.ofSeconds(1))
                .map(i -> 10 / (i - 10));


          map.//onErrorStop： 错误后停止流. 源头中断，所有监听者全部结束; 错误结束
//                 onErrorComplete() //把错误结束信号，替换为正常结束信号； 正常结束
                subscribe(v-> System.out.println("v = " + v),
                        err-> System.out.println("err = " + err),
                        ()-> System.out.println("流正常结束"));

          map.subscribe(v-> System.out.println("v1 = " + v),
                  err-> System.out.println("err1 = " + err),
                  ()-> System.out.println("流正常结束1"));


        System.in.read();

    }

    class BusinessException extends RuntimeException {
        public BusinessException(String msg) {
            super(msg);
        }
    }

    Mono<String> hahaha(Throwable throwable) {
        if (throwable.getClass() == NullPointerException.class) {

        }

        return Mono.just("哈哈-" + throwable.getMessage());
    }

    /**
     * zip: 无法结对的元素会被忽略；
     * 最多支持8流压缩；
     */
    @Test
    void zip() {
        //Tuple：元组；
        // Flux< Tuple2:<Integer,String> >


//        Flux.zip(Flux.just(1,2),Flux.just(1,2),Flux.just(2,3),Flux.just(1))
//                .map(tuple->{
//                    tuple.get
//                })


//        Flux.just(1,2,3)
//                .zipWith(Flux.just("a","b","c","d"))
//                .map(tuple -> {
//                    Integer t1 = tuple.getT1(); //元组中的第一个元素
//                    String t2 = tuple.getT2();// 元组中的第二个元素
//                    return t1 + "==>" + t2;
//                })
//                .log()
//                .subscribe(v-> System.out.println("v = " + v));


    }

    /**
     * concat： 连接； A流 所有元素和 B流所有元素拼接
     * merge：合并； A流 所有元素和 B流所有元素 按照时间序列合并
     * mergeWith：
     * mergeSequential： 按照哪个流先发元素排队
     */
    @Test
    void merge() throws IOException {

        Flux.mergeSequential();

        Flux.merge(
                        Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1)),
                        Flux.just("a", "b").delayElements(Duration.ofMillis(1500)),
                        Flux.just("haha", "hehe", "heihei", "xixi").delayElements(Duration.ofMillis(500)))
                .log()
                .subscribe();

        Flux.just(1, 2, 3).mergeWith(Flux.just(4, 5, 6));

        System.in.read();
    }

    /**
     * defaultIfEmpty:  静态兜底数据
     * switchIfEmpty:  空转换； 调用动态兜底方法；  返回新流数据
     */
    @Test
    void empty() {
        //Mono.just(null);//流里面有一个null值元素
        //Mono.empty();//流里面没有元素，只有完成信号/结束信号


        haha()
                .switchIfEmpty(hehe())//如果发布者元素为null，指定默认值，否则用发布者的值；
                .subscribe(v -> System.out.println("v = " + v));

    }

    Mono<String> hehe() {
        return Mono.just("兜底数据...");
    }

    Mono<String> haha() {
        return Mono.empty();
    }

    /**
     * transform:
     * transformDeferred:
     * *      * 、、、
     */
    @Test
    //把流变形成新数据
    void transform() {

        AtomicInteger atomic = new AtomicInteger(0);

        Flux<String> flux = Flux.just("a", "b", "c")
                .transform(values -> {
                    // ++atomic
                    if (atomic.incrementAndGet() == 1) {
                        //如果是：第一次调用，老流中的所有元素转成大写
                        return values.map(String::toUpperCase);
                    } else {
                        //如果不是第一次调用，原封不动返回
                        return values;
                    }
                });

        //transform 无defer，不会共享外部变量的值。 无状态转换; 原理，无论多少个订阅者，transform只执行一次
        //transform 有defer，会共享外部变量的值。   有状态转换; 原理，无论多少个订阅者，每个订阅者transform都只执行一次
        flux.subscribe(v -> System.out.println("订阅者1：v = " + v));
        flux.subscribe(v -> System.out.println("订阅者2：v = " + v));
    }


    /***
     * concatMap： 一个元素可以 变很多单个； 对于元素类型无限制
     * concat： Flux.concat; 静态调用
     * concatWith： 连接的流和老流中的元素类型要一样
     *
     */
    @Test
    void concatMap() {

        Flux.just(1, 2)
                .concatWith(Flux.just(4, 5, 6))
                .log()
                .subscribe();

        //连接
//        Flux.concat(Flux.just(1,2),Flux.just("h","j"),Flux.just("haha","hehe"))
//                .log()
//                .subscribe();

        //Mono、FLux：发布者
//        Flux.just(1,2)
//                .concatMap(s->  Flux.just(s+"->a",1))
//                .log()
//                .subscribe();

    }

    /**
     * flatMap、
     */
    @Test
    //扁平化
    void flatrMap() {
        Flux.just("zhang san", "li si")
                .flatMap(v -> {
                    String[] s = v.split(" ");
                    return Flux.fromArray(s); //把数据包装成多元素流
                })
                .log()
                .subscribe();//两个人的名字，按照空格拆分，打印出所有的姓与名
    }

    /**
     * filter、
     * onSubscribe：流被订阅
     * request(unbounded)：请求无限数据
     * onNext(2): 每个数据到达
     * onNext(4): 每个数据达到
     * onComplete：流结束
     */
    @Test
    void filter() {

        Flux.just(1, 2, 3, 4)  //流发布者
                .log() // 1,2,3,4
                .filter(s -> s % 2 == 0) //过滤偶数, 消费上面的流，request(1); request(1);
//                .log() // 2,4
                .subscribe(); //最终消费者;
    }
}
