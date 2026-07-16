package com.xu.user.interceptor;

import com.xu.user.content.UserContext;
import com.xu.user.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取token
        String token = jwtUtil.getTokenFromRequest(request);
        if (token == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录，请先登录\"}");
            return false;
        }

        // 2. 校验token有效性
        if (!jwtUtil.verifyToken(token)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"Token无效或已过期，请重新登录\"}");
            return false;
        }

        // 3. 存入登录用户ID，接口中直接获取
        Long loginUserId = jwtUtil.getUserId(token);
        UserContext.setLoginUserId(loginUserId);
        return true;
    }
}