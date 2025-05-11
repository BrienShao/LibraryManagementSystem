package com.example.librarymanagementsystem.interceptors;

import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.utils.JwtUtil;
import com.example.librarymanagementsystem.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        // 令牌验证
        String token = request.getHeader("Authorization");

        // 验证token
        try {
            // 解析token
            Map<String, Object> map = JwtUtil.parseToken(token);
            // 把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(map);
            // 放行
            return true;
        } catch (Exception e) {
            // 不放行
            throw new BusinessException(401, "用户未登录");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
