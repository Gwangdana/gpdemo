package com.dana.fpds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FPDS业务微服务启动类（Oracle数据源）
 */
@SpringBootApplication
public class FpdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpdsApplication.class, args);
    }
}
