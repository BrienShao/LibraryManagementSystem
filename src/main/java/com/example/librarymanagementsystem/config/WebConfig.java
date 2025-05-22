package com.example.librarymanagementsystem.config;

import com.example.librarymanagementsystem.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 配置可以被跨域的路径
                .allowedOriginPatterns("*") // 允许所有的请求域名访问我们的跨域资源
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 允许任何方法（POST、GET等）
                .allowedHeaders("*") // 允许任何请求头
                .allowCredentials(true) // 允许携带凭证（如 cookie）
                .maxAge(3600); // 预检请求的有效期，单位为秒
    }
}
