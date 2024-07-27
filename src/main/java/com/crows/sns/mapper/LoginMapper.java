package com.crows.sns.mapper;

import com.crows.sns.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    public User findByUsername(String username);
}
