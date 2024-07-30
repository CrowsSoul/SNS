package com.crows.sns.controller.user;

import com.crows.sns.pojo.LoginRequest;
import com.crows.sns.pojo.LoginResponse;
import com.crows.sns.service.Impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        return loginService.login(username, password);
    }

}
