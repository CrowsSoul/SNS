package com.crows.sns.service.Impl;

import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.RegisterResponse;
import com.crows.sns.pojo.User;
import com.crows.sns.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public RegisterResponse register(String nickname, String role, String password, String username) {
        if(userMapper.findByUsername(username)!=null)
        {
            return new RegisterResponse(false, "用户名已存在！");
        }
        else
        {
            User newUser = new User(username,password,nickname,role);
            userMapper.insertUser(newUser);
            return new RegisterResponse(true, "注册成功！");
        }
    }
}
