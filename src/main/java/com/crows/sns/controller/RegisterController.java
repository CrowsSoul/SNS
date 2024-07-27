package com.crows.sns.controller;

import com.crows.sns.pojo.RegisterResponse;
import com.crows.sns.pojo.RegisterRequest; // 引入 RegisterRequest 类
import com.crows.sns.service.Impl.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private RegisterServiceImpl registerService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String nickname = registerRequest.getNickname();
        String role = registerRequest.getRole();

        // 处理注册逻辑
        return registerService.register(nickname,role,password,username);
    }
}

