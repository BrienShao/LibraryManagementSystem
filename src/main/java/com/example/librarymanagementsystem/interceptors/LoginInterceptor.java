package com.example.librarymanagementsystem.interceptors;

import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.utils.JwtUtil;
import com.example.librarymanagementsystem.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        /**
         * 实际上发送了两次请求，第一次为 OPTIONS 请求，第二次才 GET/POST… 请求
         * 在OPTIONS请求中，不会携带请求头的参数，所以在拦截器上获取请求头为空，自定义的拦截器拦截成功
         * 第一次请求不能通过，就不能获取第二次的请求了 GET/POST…
         * 第一次请求不带参数，第二次请求才带参数
         *
         * 如果请求为 OPTIONS 请求，则返回 true，表示可以正常访问，然后就会收到真正的 GET/POST 请求
         */
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        // 令牌验证
        String token = request.getHeader("Authorization");

        // 验证token
        try {
            // 从 Redis 中获取该用户的 token
            if (stringRedisTemplate.opsForValue().get(token) == null) {
                throw new BusinessException(401, "token已失效");
            }
            // 解析前端传过来的 token
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
