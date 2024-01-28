package com.atguigu.webflux.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author lfy
 * @Description
 * @create 2023-12-01 20:52
 */
@ResponseBody
    @Controller
public class HelloController {


    //WebFlux： 向下兼容原来SpringMVC的大多数注解和API；
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "key",required = false,defaultValue = "哈哈") String key,
                        ServerWebExchange exchange,
                        WebSession webSession,
                        HttpMethod method,
                        HttpEntity<String> entity,
                        @RequestBody String s,
                        FilePart file){

//        file.transferTo() //零拷贝技术；
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String name = method.name();



        Object aaa = webSession.getAttribute("aaa");
        webSession.getAttributes().put("aa","nn");

        return "Hello World!!! key="+key;
    }



    // Rendering：一种视图对象。
    @GetMapping("/bai")
    public Rendering render(){
//        Rendering.redirectTo("/aaa"); //重定向到当前项目根路径下的 aaa
       return   Rendering.redirectTo("http://www.baidu.com").build();
    }

    //现在推荐的方式
    //1、返回单个数据Mono： Mono<Order>、User、String、Map
    //2、返回多个数据Flux： Flux<Order>
    //3、配合Flux，完成SSE： Server Send Event； 服务端事件推送

    @GetMapping("/haha")
    public Mono<String> haha(){

//        ResponseEntity.status(305)
//                .header("aaa","bbb")
//                .contentType(MediaType.APPLICATION_CBOR)
//                .body("aaaa")
//                .

        return Mono.just(0)
                .map(i-> 10/i)
                .map(i->"哈哈-"+i);
    }

    @GetMapping("/hehe")
    public Flux<String> hehe(){
        return Flux.just("hehe1","hehe2");
    }


    //text/event-stream
    //SSE测试； chatgpt都在用； 服务端推送
    @GetMapping(value = "/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> sse(){
        return Flux.range(1,10)
                .map(i-> {

                    //构建一个SSE对象
                   return    ServerSentEvent.builder("ha-" + i)
                            .id(i + "")
                            .comment("hei-" + i)
                            .event("haha")
                            .build();
                })
                .delayElements(Duration.ofMillis(500));
    }

    //SpringMVC 以前怎么用，基本可以无缝切换。
    // 底层：需要自己开始编写响应式代码





}
