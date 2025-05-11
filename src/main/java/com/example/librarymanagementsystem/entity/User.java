package com.example.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull    // 值不允许为空
    private Long id;
    @NotNull    // 值不允许为空，包括空字符串
    private String username;    // 用户名
    @JsonIgnore // SpringMVC把当前对象转换成json字符串时，忽略此属性
    private String password;    // 密码
    private String gender;  // 性别
    private Integer amount; // 可借数量
    private String phone;   // 电话号码
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;    // 昵称
    @NotEmpty
    @Email  //验证邮箱格式
    private String email;   // 邮箱
    private String avatar;  // 头像
    private LocalDateTime birthday;  // 生日
    private Integer isAdmin;    // 是否为管理员（ 1 管理员 0 普通用户）
    private LocalDateTime createTime;    // 创建时间
    private LocalDateTime updateTime;    // 更新时间
}
