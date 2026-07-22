package com.dana.common.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置属性
 */
@Data
@ConfigurationProperties(prefix = "common.auth")
public class AuthProperties {

    /**
     * 是否启用鉴权拦截器（默认启用）
     */
    private boolean enabled = true;

    /**
     * 白名单路径（不需要鉴权），支持 Ant 风格
     */
    private List<String> excludePaths = new ArrayList<>();
}
