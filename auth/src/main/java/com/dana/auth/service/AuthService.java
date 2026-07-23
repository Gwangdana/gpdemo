package com.dana.auth.service;

import com.dana.auth.dto.LoginRequest;
import com.dana.auth.dto.LoginResponse;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应（含 Token）
     */
    LoginResponse login(LoginRequest request);

    /**
     * 用户登出（使 Token 失效）
     *
     * @param token 访问令牌
     */
    void logout(String token);
}
