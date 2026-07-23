package com.dana.common.security.config;

import com.dana.common.security.AuthInterceptor;
import com.dana.common.security.AuthProperties;
import com.dana.common.security.CommonJwtUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * common-security 自动配置
 * <p>
 * 当引入 common-security 依赖的 Spring MVC 应用启动时，自动注册：
 * - JWT 工具类
 * - 鉴权拦截器（可通过 common.auth.enabled=false 关闭）
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(AuthProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommonJwtUtil commonJwtUtil() {
        return new CommonJwtUtil();
    }

    @Bean
    @ConditionalOnProperty(prefix = "common.auth", name = "enabled", havingValue = "true", matchIfMissing = true)
    public AuthInterceptor authInterceptor(CommonJwtUtil jwtUtil,
                                           StringRedisTemplate redisTemplate,
                                           AuthProperties authProperties) {
        return new AuthInterceptor(jwtUtil, redisTemplate, authProperties);
    }

    /**
     * 注册鉴权拦截器
     */
    @Bean
    @ConditionalOnProperty(prefix = "common.auth", name = "enabled", havingValue = "true", matchIfMissing = true)
    public WebMvcConfigurer securityWebMvcConfigurer(AuthInterceptor authInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authInterceptor)
                        .addPathPatterns("/**")
                        .excludePathPatterns(
                                "/actuator/**",
                                "/error"
                        );
            }
        };
    }
}
