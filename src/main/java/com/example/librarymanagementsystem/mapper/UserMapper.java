package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where id = #{id}")
    User findById(Long id);

    // 根据用户名查询用户
    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    // 创建新用户
    @Insert("insert into user(username, password, nickname, amount, gender, birthday, create_time, update_time)" +
            "values(#{username}, #{password}, #{username}, 5, '男', '2000-01-01', now(), now())")
    void createUser(String username, String password);

    // 更新用户信息
    @Update("update user set " +
            "gender = #{gender}, " +
            "amount = #{amount}, " +
            "phone = #{phone}, " +
            "nickname = #{nickname}, " +
            "email = #{email}, " +
            "birthday = #{birthday}, " +
            "is_admin = #{isAdmin}, " +
            "update_time = #{updateTime} where id = #{id}")
    void update(User user);

    // 更新头像
    @Update("update user set avatar = #{avatarUrl}, update_time = now() where id = #{id}")
    void updateAvatar(String avatarUrl, Long id);

    // 修改密码
    @Update("update user set password = #{encodePassword}, update_time = now() where id = #{id}")
    void updatePassword(String encodePassword, Long id);
}
