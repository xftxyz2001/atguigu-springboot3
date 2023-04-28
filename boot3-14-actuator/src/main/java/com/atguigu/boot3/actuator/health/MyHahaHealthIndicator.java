package com.atguigu.boot3.actuator.health;

import com.atguigu.boot3.actuator.component.MyHahaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author lfy
 * @Description
 * @create 2023-05-08 22:59
 *
 * 1、实现 HealthIndicator 接口来定制组件的健康状态对象（Health） 返回
 * 2、
 */
@Component
public class MyHahaHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    MyHahaComponent myHahaComponent;
    /**
     * 健康检查
     * @param builder
     * @throws Exception
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        //自定义检查方法

        int check = myHahaComponent.check();
        if(check == 1){
            //存活
            builder.up()
                    .withDetail("code","1000")
                    .withDetail("msg","活的很健康")
                    .withDetail("data","我的名字叫haha")
                    .build();
        }else {
            //下线
            builder.down()
                    .withDetail("code","1001")
                    .withDetail("msg","死的很健康")
                    .withDetail("data","我的名字叫haha完蛋")
                    .build();
        }

    }
}
