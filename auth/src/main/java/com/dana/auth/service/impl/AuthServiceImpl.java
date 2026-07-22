package com.dana.auth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dana.auth.dto.LoginRequest;
import com.dana.auth.dto.LoginResponse;
import com.dana.auth.entity.SysUser;
import com.dana.auth.mapper.SysUserMapper;
import com.dana.auth.service.AuthService;
import com.dana.auth.service.TokenService;
import com.dana.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserMapper sysUserMapper;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. 查询用户
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, request.getUsername())
        );
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 2. 校验密码
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 3. 校验状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }

        // 4. 生成 Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 5. 将 Token 存入 Redis
        tokenService.storeToken(token, user.getId());

        // 6. 返回登录结果
        return LoginResponse.builder()
                .token(token)
                .expireIn(jwtUtil.getExpirationMillis())
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .build();
    }

    @Override
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (token != null) {
            tokenService.invalidateToken(token);
        }
    }
}
