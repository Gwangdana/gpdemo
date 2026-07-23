package com.dana.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

/**
 * Redisson 自动配置
 * <p>
 * 当 classpath 中存在 Redisson 时自动注册 RedissonClient
 * 复用 spring.data.redis 的连接配置，避免重复定义
 */
@AutoConfiguration
@ConditionalOnClass(Redisson.class)
public class RedissonAutoConfiguration {

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.database:0}")
    private int database;

    @Value("${spring.data.redis.timeout:5000ms}")
    private Duration timeout;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setDatabase(database)
                .setTimeout((int) timeout.toMillis())
                .setConnectionMinimumIdleSize(2)
                .setConnectionPoolSize(8);

        if (password != null && !password.isEmpty()) {
            serverConfig.setPassword(password);
        }

        return Redisson.create(config);
    }
}
