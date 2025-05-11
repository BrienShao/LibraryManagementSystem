package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

     Long getUserId();

     List<User> getAllUsers();

     User findById(Long id);

     // 根据用户名查询用户
     User findByUserName(String username);

     // 注册新用户
     User createUser(String username, String password);

     // 用户登录
     String login(String username, String password);

     // 当前用户信息
     User userInfo();

     // 更新用户信息
     void update(User user);

     // 更新头像
     void updateAvatar(String avatarUrl);

     // 修改密码
     void updatePassword(Map<String, String> params);
}
