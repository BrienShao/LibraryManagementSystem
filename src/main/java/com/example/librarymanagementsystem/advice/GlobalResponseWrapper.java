package com.example.librarymanagementsystem.advice;

import com.example.librarymanagementsystem.entity.Result;
import com.google.gson.Gson;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    // 判断是否需要对响应进行处理
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 排除已包装的 Result 类型和 Spring 默认响应（如 ResponseEntity）
        return !returnType.getParameterType().equals(Result.class)
                && !returnType.getParameterType().equals(ResponseEntity.class);
    }

    // 包装响应体
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 处理 String 类型返回值（避免转换错误）
        if (body instanceof String) {
            return new Gson().toJson(Result.success(body));
        }
        // 其他类型直接包装
        return Result.success(body);
    }
}
