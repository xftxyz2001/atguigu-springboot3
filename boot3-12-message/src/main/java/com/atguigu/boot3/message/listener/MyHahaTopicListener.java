package com.atguigu.boot3.message.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 16:39
 */
@Component
public class MyHahaTopicListener {

    //默认的监听是从消息队列最后一个消息开始拿。新消息才能拿到
    @KafkaListener(topics="newshaha",groupId="haha")
    public void haha(ConsumerRecord record){
        //1、获取消息的各种详细信息
//        String topic = record.topic();
        Object key = record.key();
        Object value = record.value();
        System.out.println("收到消息：key【"+key+"】 value【"+value+"】");
    }


    //拿到以前的完整消息;
    @KafkaListener(groupId = "hehe",topicPartitions={
            @TopicPartition(topic="newshaha",partitionOffsets={
                    @PartitionOffset(partition="0",initialOffset = "0")
            })
    })
    public void hehe(ConsumerRecord record){
        Object key = record.key();
        Object value = record.value();
        System.out.println("======收到消息：key【"+key+"】 value【"+value+"】");
    }
}
