package com.crows.sns.mapper;

import com.crows.sns.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    public User findByUsername(String username);
}
