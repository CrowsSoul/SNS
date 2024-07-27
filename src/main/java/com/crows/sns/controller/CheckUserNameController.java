package com.crows.sns.controller;

import com.crows.sns.mapper.LoginMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.CheckUserNameResponse;
import com.crows.sns.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckUserNameController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/check-username")
    public CheckUserNameResponse checkUserName(@RequestParam("username") String username) {
        // 检查用户名逻辑
        User user = userMapper.findByUsername(username);
        if(user == null) { return new CheckUserNameResponse(true); }
        else { return new CheckUserNameResponse(false); }
    }
}

