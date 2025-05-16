package com.example.librarymanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;


@SpringBootTest
class LibraryManagementSystemApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void redisSetTest(){
        // 往 Redis 中存储一个键值对 StringRedisTemplate
        stringRedisTemplate.opsForValue().set("username","zhangsan", 15, TimeUnit.SECONDS);
    }

    @Test
    void redisGetTest(){
        System.out.println(stringRedisTemplate.opsForValue().get("username"));
    }
}
