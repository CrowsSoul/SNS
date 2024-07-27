package com.crows.sns.service.Impl;

import com.crows.sns.mapper.LoginMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.LoginResponse;
import com.crows.sns.pojo.User;
import com.crows.sns.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginResponse login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return new LoginResponse(null, false, "用户不存在", null);
        }

        String password_db = user.getPassword();
        String nickname = user.getNickname();
        String role = user.getRole();

        if (!password_db.equals(password)) {
            return new LoginResponse(null, false, "密码错误", null);
        } else {
            return new LoginResponse(nickname, true, "登录成功", role);
        }
    }

}
