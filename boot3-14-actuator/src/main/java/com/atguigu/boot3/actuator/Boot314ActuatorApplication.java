package com.atguigu.boot3.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 整合Prometheus+Grafana 完成线上应用指标监控系统
 * 1、改造SpringBoot应用，产生Prometheus需要的格式数据
 *   - 导入 micrometer-registry-prometheus
 * 2、部署java应用。在同一个机器内，访问 http://172.25.170.71:9999/actuator/prometheus 就能得到指标数据
 *    在外部访问：http://8.130.32.70:9999/actuator/prometheus
 * 3、修改prometheus配置文件，让他拉取某个应用的指标数据
 * 4、去grafana添加一个prometheus数据源，配置好prometheus地址
 *
 */
@SpringBootApplication
public class Boot314ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot314ActuatorApplication.class, args);
    }

}
