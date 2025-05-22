package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.AuthResponse;
import com.example.librarymanagementsystem.entity.Result;
import com.example.librarymanagementsystem.entity.User;
import com.example.librarymanagementsystem.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/register")
    public void register(@RequestParam @Pattern(regexp = "^\\S{5,16}$") String username, @RequestParam @Pattern(regexp = "^\\S{5,16}$") String password) {
        userService.createUser(username, password);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/info")
    public User userInfo() {
        return userService.userInfo();
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    @PutMapping("/info")
    public void update(@RequestBody @Validated User user) {
        userService.update(user);
    }

    /**
     * 更新头像
     *
     * @param avatarUrl 头像地址
     */
    @PatchMapping("/updateAvatar")
    public void updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
    }

    /**
     * 修改密码
     *
     * @param passwordData 修改密码信息
     */
    @PutMapping("/password")
    public void updatePassword(@RequestBody Map<String, String> passwordData, @RequestHeader(value = "Authorization") String token) {
        userService.updatePassword(passwordData);
        // 删除 Redis 中对应的 token
        stringRedisTemplate.delete(token);

    }

    /**
     * 用户列表
     */
    @GetMapping("/getAllUsers")
    public Result<List<User>> getAllUsers() {
        return Result.success(userService.getAllUsers());
    }

    /**
     * 通过 ID 获取单个用户信息
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }
}
