package com.atguigu.boot3.core.controller;

import com.atguigu.boot3.core.entity.UserEntity;
import com.atguigu.boot3.core.event.EventPublisher;
import com.atguigu.boot3.core.event.LoginSuccessEvent;
import com.atguigu.boot3.core.service.AccountService;
import com.atguigu.boot3.core.service.CouponService;
import com.atguigu.boot3.core.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lfy
 * @Description
 * @create 2023-04-24 18:41
 */
@RestController
public class LoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    CouponService couponService;

    @Autowired
    SysService sysService;

    @Autowired
    EventPublisher eventPublisher;


    /**
     * 增加业务
     * @param username
     * @param passwd
     * @return
     */
    @GetMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("passwd")String passwd){
        //业务处理登录
        System.out.println("业务处理登录完成....");

        //TODO 发送事件.
        //1、创建事件信息
        LoginSuccessEvent event = new LoginSuccessEvent(new UserEntity(username, passwd));
        //2、发送事件
        eventPublisher.sendEvent(event);


        //1、账户服务自动签到加积分
//        accountService.addAccountScore(username);
//        //2、优惠服务随机发放优惠券
//        couponService.sendCoupon(username);
//        //3、系统服务登记用户登录的信息
//        sysService.recordLog(username);

        //设计模式：对新增开放，对修改关闭
        //xxx
        //xxx
        //xxx
        return username+"登录成功";
    }
}
