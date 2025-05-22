package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.entity.AuthResponse;
import com.example.librarymanagementsystem.entity.User;
import com.example.librarymanagementsystem.exception.BusinessException;
import com.example.librarymanagementsystem.mapper.UserMapper;
import com.example.librarymanagementsystem.service.UserService;
import com.example.librarymanagementsystem.utils.Argon2Util;
import com.example.librarymanagementsystem.utils.JwtUtil;
import com.example.librarymanagementsystem.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Long getUserId() {
        Map<String, Object> map = ThreadLocalUtil.getThreadLocal();
        return Long.valueOf((Integer) map.get("id"));
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void createUser(String username, String password) {
        // 检查用户名是否存在
        User user = this.findByUserName(username);
        if (user == null) {
            // 加密用户密码
            String encodedPassword = Argon2Util.encodePassword(password);
            userMapper.createUser(username, encodedPassword);
        } else {
            throw new BusinessException("用户名已存在");
        }
    }

    @Override
    public AuthResponse login(String username, String password) {
        // 根据用户名查询用户
        User user = this.findByUserName(username);

        if (user.getUsername() == null || !Argon2Util.matchesPassword(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.generateToken(claims);
        // 把 token 存储到 Redis 中
        stringRedisTemplate.opsForValue().set(token, token, 24, TimeUnit.HOURS);
        return new AuthResponse(token, user);
    }

    @Override
    public User userInfo() {
        return this.findById(this.getUserId());
    }

    @Override
    public void update(User user) {
        // 是否本人修改
        Long id = getUserId();
        if (!id.equals(user.getId())) {
            throw new BusinessException(403, "用户权限不足");
        }
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        userMapper.updateAvatar(avatarUrl, getUserId());
    }

    @Override
    public void updatePassword(Map<String, String> passwordData) {
        // 校验密码
        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");
        String confirmPassword = passwordData.get("confirmPassword");

        // 非空校验
        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(confirmPassword)) {
            throw new BusinessException("密码不能为空");
        }

        // 确认密码一致性
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException("密码填写不一致");
        }

        // 当前登录用户
        User loginUser = this.userInfo();
        // 新密码不能与旧密码相同
        if (Argon2Util.matchesPassword(newPassword, loginUser.getPassword())) {
            throw new BusinessException("新密码与旧密码相同");
        }
        // 校验原密码
        if (Argon2Util.matchesPassword(oldPassword, loginUser.getPassword())) {
            String encodePassword = Argon2Util.encodePassword(newPassword);
            userMapper.updatePassword(encodePassword, loginUser.getId());
        } else {
            throw new BusinessException("原密码不正确");
        }
    }
}
