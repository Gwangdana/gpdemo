package com.dana.auth.service.impl;

import com.dana.auth.service.TokenService;
import com.dana.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Token 管理服务实现（基于 Redis）
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_PREFIX = "auth:token:";

    private final StringRedisTemplate redisTemplate;
    private final JwtUtil jwtUtil;

    @Override
    public void storeToken(String token, Long userId) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, String.valueOf(userId),
                jwtUtil.getExpirationMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean validateToken(String token) {
        String key = TOKEN_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public Claims parseToken(String token) {
        return jwtUtil.parseToken(token);
    }

    @Override
    public void invalidateToken(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.delete(key);
    }
}
