package com.example.librarymanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 统一响应类
 * @param <T>
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;    // 状态码（如 200=成功，400=客户端错误，500=服务端错误）
    private String msg;  // 提示消息
    private T data;      // 响应数据

    // 快速生成成功响应（无数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 快速生成成功响应（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 快速生成失败响应
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
