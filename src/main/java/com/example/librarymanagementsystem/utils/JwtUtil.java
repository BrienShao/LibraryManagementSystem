package com.example.librarymanagementsystem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "libraryManagementSystem";

    // 接收业务数据，生成token并返回
    public static String generateToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(60 * 60 * 24)))  // 当前时刻3600秒后时间
                .sign(Algorithm.HMAC256(KEY));
    }

    // 接收token，验证token，并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build().verify(token)
                .getClaim("claims")
                .asMap();
    }
}
