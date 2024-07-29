package com.crows.sns.mapper;

import com.crows.sns.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    public User findByUsername(String username);

    @Insert("INSERT INTO users (username, nickname, password, email, role, created_time, updated_time) " +
            "VALUES (#{username}, #{nickname}, #{password}, #{email}, #{role}, NOW(), NOW())")
    void insertUser(User user);

    @Select("SELECT nickname FROM users WHERE user_id = #{id}")
    public String findNicknameById(int id);

    @Select("SELECT user_id FROM users WHERE nickname = #{nickname}")
    public int findUserIdByNickname(String nickname);
}
