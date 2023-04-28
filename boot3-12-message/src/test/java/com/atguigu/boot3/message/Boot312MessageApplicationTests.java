package com.atguigu.boot3.message;

import com.atguigu.boot3.message.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class Boot312MessageApplicationTests {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Test
    void contextLoads() {

        StopWatch stopWatch = new StopWatch();

        CompletableFuture[] futures = new CompletableFuture[10000];

        stopWatch.start();
        for (int i = 0; i < 10000; i++) {
            //JUC
            CompletableFuture future = kafkaTemplate.send("newshaha", "haha-"+i, "哈哈哈-"+i);
            futures[i] = future;
        }

        CompletableFuture.allOf(futures)
                .join();

        stopWatch.stop();

        long millis = stopWatch.getTotalTimeMillis();
        System.out.println("10000消息发送完成：ms时间："+millis);

    }

    @Test
    void send(){
        CompletableFuture future = kafkaTemplate.send("newshaha", "person", new Person(1L, "张三", "hjaha@qq.com"));

        future.join();
        System.out.println("消息发送成功...");
    }

}
