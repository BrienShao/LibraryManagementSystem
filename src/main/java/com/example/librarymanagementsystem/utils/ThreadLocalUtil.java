package com.example.librarymanagementsystem.utils;

/**
 *
 * ThreadLocalUtil工具类
 *
 */
public class ThreadLocalUtil {

    // 提供ThreadLocal对象
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    // 存储键值对
    public static void set(Object value) {
        threadLocal.set(value);
    }

    // 根据键获取值
    public static <T> T getThreadLocal() {
        return (T) threadLocal.get();
    }

    // 清除 ThreadLocal 防止内存泄露
    public static void remove() {
        threadLocal.remove();
    }
}
