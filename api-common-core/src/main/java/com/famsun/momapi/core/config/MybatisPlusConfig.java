package com.famsun.momapi.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件（MP 3.5+ 必须配置，否则分页查询会失效）
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 指定数据库类型为 MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    static {
        // 全局开启驼峰转换（SpringBoot默认开启，显式声明增强可读性）
        com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig dbConfig =
                new com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig();
        dbConfig.setColumnLike(true);
    }
}
