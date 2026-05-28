package com.famsun.momapi.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.famsun.momapi.core",
        "com.famsun.momapi.security",
        "com.famsun.momapi.app"
})
@MapperScan("com.famsun.momapi.app.mapper")
public class ApiProxyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProxyAppApplication.class, args);
        System.out.println("===== API代理中台启动成功 =====");
    }
}
