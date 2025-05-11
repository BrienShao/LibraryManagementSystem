package com.example.librarymanagementsystem.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Argon2加密工具类
public class Argon2Util {
    // 参数：盐长度、哈希长度、并行度、内存（KiB）、迭代次数
    private static final PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1, 15 * 1024, 2);

    // 参数：前端传过来的明文密码
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // 参数：前端传过来的明文密码、数据库保存的加密密码
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
