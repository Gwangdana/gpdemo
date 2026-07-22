package com.dana.base.interceptor;

import com.dana.base.util.BaseJwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 鉴权拦截器：校验请求中的 Bearer Token
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_PREFIX = "auth:token:";

    private final BaseJwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorized(response, "缺少有效的 Authorization 请求头");
            return false;
        }

        String token = authHeader.substring(7);

        // 1. 校验 JWT 签名
        Claims claims = jwtUtil.parseToken(token);
        if (claims == null) {
            sendUnauthorized(response, "Token 无效或已过期");
            return false;
        }

        // 2. 校验 Redis 中是否存在（防止已登出或伪造 Token）
        String redisKey = TOKEN_PREFIX + token;
        Boolean exists = redisTemplate.hasKey(redisKey);
        if (!Boolean.TRUE.equals(exists)) {
            sendUnauthorized(response, "Token 已失效，请重新登录");
            return false;
        }

        // 将用户信息注入到 request 中，方便后续使用
        String userId = claims.getSubject();
        String username = claims.get("username", String.class);
        request.setAttribute("userId", userId);
        request.setAttribute("username", username);

        return true;
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"" + message + "\",\"data\":\"\"}");
    }
}
