package com.famsun.momapi.security.utils;

import java.util.UUID;

public class TokenUtils {

    // Token Redis Key前缀（统一管理，避免冲突）
    public static final String TOKEN_KEY_PREFIX = "api_proxy:token:";
    // Token过期时间：2小时（单位：秒）
    public static final long TOKEN_EXPIRE_SECONDS = 2 * 60 * 60;

    /**
     * 生成随机Token（UUID，无特殊字符，避免URL问题）
     */
    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 拼接Redis Key
     */
    public static String buildRedisKey(String token) {
        return TOKEN_KEY_PREFIX + token;
    }
}
