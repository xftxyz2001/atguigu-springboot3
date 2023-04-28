package com.atguigu.boot3.rpc.service;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * @author lfy
 * @Description
 * @create 2023-05-07 12:33
 */

public interface WeatherInterface {


    @GetExchange(url = "https://ali-weather.showapi.com/area-to-weather-date",accept = "application/json")
    Mono<String> getWeather(@RequestParam("area") String city);
}
