package com.dana.auth.service;

import io.jsonwebtoken.Claims;

/**
 * Token 管理服务接口（供网关调用校验）
 */
public interface TokenService {

    /**
     * 将 Token 存入 Redis（登录时调用）
     *
     * @param token  JWT 字符串
     * @param userId 用户ID
     */
    void storeToken(String token, Long userId);

    /**
     * 校验 Token 是否有效（Redis 中存在且未过期）
     *
     * @param token JWT 字符串
     * @return 是否有效
     */
    boolean validateToken(String token);

    /**
     * 从 Token 中解析 Claims
     *
     * @param token JWT 字符串
     * @return Claims 或 null
     */
    Claims parseToken(String token);

    /**
     * 使 Token 失效（登出时调用）
     *
     * @param token JWT 字符串
     */
    void invalidateToken(String token);
}
