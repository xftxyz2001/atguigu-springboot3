package com.atguigu.reactor;

import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.*;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lfy
 * @Description
 * @create 2023-11-23 20:58
 */
public class FluxDemo {

    public static void main(String[] args) {
//        Flux.concat(Flux.just(1,2,3),Flux.just(7,8,9))
//                .subscribe(System.out::println);


        Flux.range(1, 7)
//                .log() //日志   onNext(1~7)
                .filter(i -> i > 3) //挑出>3的元素
//                .log() //onNext(4~7)
                .map(i -> "haha-" + i)
                .log()  // onNext(haha-4 ~ 7)
                .subscribe(System.out::println);


        //今天： Flux、Mono、弹珠图、事件感知API、每个操作都是操作的上个流的东西
    }


    /**
     * 响应式编程核心：看懂文档弹珠图；
     * 信号： 正常/异常（取消）
     * SignalType：
     *      SUBSCRIBE： 被订阅
     *      REQUEST：  请求了N个元素
     *      CANCEL： 流被取消
     *      ON_SUBSCRIBE：在订阅时候
     *      ON_NEXT： 在元素到达
     *      ON_ERROR： 在流错误
     *      ON_COMPLETE：在流正常完成时
     *      AFTER_TERMINATE：中断以后
     *      CURRENT_CONTEXT：当前上下文
     *      ON_CONTEXT：感知上下文
     * <p>
     * doOnXxx API触发时机
     *      1、doOnNext：每个数据（流的数据）到达的时候触发
     *      2、doOnEach：每个元素（流的数据和信号）到达的时候触发
     *      3、doOnRequest： 消费者请求流元素的时候
     *      4、doOnError：流发生错误
     *      5、doOnSubscribe: 流被订阅的时候
     *      6、doOnTerminate： 发送取消/异常信号中断了流
     *      7、doOnCancle： 流被取消
     *      8、doOnDiscard：流中元素被忽略的时候
     *
     * @param args
     */
    public void doOnXxxx(String[] args) {

        // 关键：doOnNext：表示流中某个元素到达以后触发我一个回调
        // doOnXxx要感知某个流的事件，写在这个流的后面，新流的前面
        Flux.just(1, 2, 3, 4, 5, 6, 7, 0, 5, 6)
                .doOnNext(integer -> System.out.println("元素到达：" + integer)) //元素到达得到时候触发
                .doOnEach(integerSignal -> { //each封装的详细
                    System.out.println("doOnEach.." + integerSignal);
                })//1,2,3,4,5,6,7,0
                .map(integer -> 10 / integer) //10,5,3,
                .doOnError(throwable -> {
                    System.out.println("数据库已经保存了异常：" + throwable.getMessage());
                })
                .map(integer -> 100 / integer)
                .doOnNext(integer -> System.out.println("元素到哈：" + integer))

                .subscribe(System.out::println);
    }


    //Mono<Integer>： 只有一个Integer
    //Flux<Integer>： 有很多Integer
    public void fluxDoOn(String[] args) throws IOException, InterruptedException {
//        Mono<Integer> just = Mono.just(1);
//
//        just.subscribe(System.out::println);

        //空流:  链式API中，下面的操作符，操作的是上面的流。
        // 事件感知API：当流发生什么事的时候，触发一个回调,系统调用提前定义好的钩子函数（Hook【钩子函数】）；doOnXxx；
        Flux<Integer> flux = Flux.range(1, 7)
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> {
                    System.out.println("流正常结束...");
                })
                .doOnCancel(() -> {
                    System.out.println("流已被取消...");
                })
                .doOnError(throwable -> {
                    System.out.println("流出错..." + throwable);
                })
                .doOnNext(integer -> {
                    System.out.println("doOnNext..." + integer);
                }); //有一个信号：此时代表完成信号

        flux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("订阅者和发布者绑定好了：" + subscription);
                request(1); //背压
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("元素到达：" + value);
                if (value < 5) {
                    request(1);
                    if (value == 3) {
                        int i = 10 / 0;
                    }
                } else {
                    cancel();//取消订阅
                }
                ; //继续要元素
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("数据流结束");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.out.println("数据流异常");
            }

            @Override
            protected void hookOnCancel() {
                System.out.println("数据流被取消");
            }

            @Override
            protected void hookFinally(SignalType type) {
                System.out.println("结束信号：" + type);
                // 正常、异常
//                try {
//                    //业务
//                }catch (Exception e){
//
//                }finally {
//                    //结束
//                }
            }
        });

        Thread.sleep(2000);


//        Flux<Integer> range = Flux.range(1, 7);


        System.in.read();
    }


    //测试Flux
    public void flux() throws IOException {
//        Mono: 0|1个元素的流
//        Flux: N个元素的流；  N>1
        //发布者发布数据流：源头


        //1、多元素的流
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5); //


        //流不消费就没用； 消费：订阅
        just.subscribe(e -> System.out.println("e1 = " + e));
        //一个数据流可以有很多消费者
        just.subscribe(e -> System.out.println("e2 = " + e));

        //对于每个消费者来说流都是一样的；  广播模式；

        System.out.println("==========");
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));//每秒产生一个从0开始的递增数字

        flux.subscribe(System.out::println);


        System.in.read();
    }
}
