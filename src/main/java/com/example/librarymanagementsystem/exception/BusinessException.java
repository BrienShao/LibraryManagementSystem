package com.example.librarymanagementsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * 业务异常基类，携带 HTTP 状态码和错误信息
 *
 */
@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        this(400, message); // 默认客户端错误码
    }
}
