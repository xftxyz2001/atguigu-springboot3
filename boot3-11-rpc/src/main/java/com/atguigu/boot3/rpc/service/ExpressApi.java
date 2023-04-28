package com.atguigu.boot3.rpc.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * @author lfy
 * @Description
 * @create 2023-05-07 12:45
 */
public interface ExpressApi {
    @GetExchange(url = "https://express3.market.alicloudapi.com/express3",accept = "application/json")
    Mono<String> getExpress(@RequestParam("number") String number);
}
