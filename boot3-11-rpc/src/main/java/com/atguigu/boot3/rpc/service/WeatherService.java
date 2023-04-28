package com.atguigu.boot3.rpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lfy
 * @Description
 * @create 2023-05-07 12:16
 */
@Service
public class WeatherService {

    @Autowired
    WeatherInterface weatherInterface;

    public Mono<String> weather(String city){
        //远程调用阿里云API

//        Mono<String> mono = getByWebClient(city);


        Mono<String> weather = weatherInterface.getWeather(city);

        return weather;
    }

    private static Mono<String> getByWebClient(String city) {
        //1、创建WebClient
        WebClient client = WebClient.create();

        //2、准备数据
        Map<String,String> params = new HashMap<>();
        params.put("area", city);
        //3、定义发请求行为  CompletableFuture
        Mono<String> mono = client.get()
                .uri("https://ali-weather.showapi.com/area-to-weather-date?area={area}", params)
                .accept(MediaType.APPLICATION_JSON) //定义响应的内容类型
                .header("Authorization", "APPCODE 93b7e19861a24c519a7548b17dc16d75") //定义请求头
                .retrieve()
                .bodyToMono(String.class);
        return mono;
    }
}
