package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.entity.Result;
import com.example.librarymanagementsystem.entity.User;
import com.example.librarymanagementsystem.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 用户注册
    @PostMapping("/register")
    public User register(@RequestParam @Pattern(regexp = "^\\S{5,16}$") String username, @RequestParam @Pattern(regexp = "^\\S{5,16}$") String password) {
        return userService.createUser(username, password);
    }

    // 用户登录
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    // 获取当前登录用户信息
    @GetMapping("/userInfo")
    public User userInfo() {
        return userService.userInfo();
    }

    // 更新用户信息
    @PutMapping("/update")
    public void update(@RequestBody @Validated User user) {
        userService.update(user);
    }

    // 更新头像
    @PatchMapping("/updateAvatar")
    public void updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
    }

    // 修改密码
    @PatchMapping("/updatePassword")
    public void updatePassword(@RequestBody Map<String, String> params) {
        userService.updatePassword(params);
    }

    @GetMapping("/getAllUsers")
    public Result<List<User>> getAllUsers() {
        return Result.success(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }
}
