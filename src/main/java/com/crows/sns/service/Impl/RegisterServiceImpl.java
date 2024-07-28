package com.crows.sns.service.Impl;

import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Response;
import com.crows.sns.pojo.User;
import com.crows.sns.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response register(String nickname, String role, String password, String username) {
        if(userMapper.findByUsername(username)!=null)
        {
            return new Response(false, "用户名已存在！");
        }
        else
        {
            User newUser = new User(username,password,nickname,role);
            userMapper.insertUser(newUser);
            return new Response(true, "注册成功！");
        }
    }
}
