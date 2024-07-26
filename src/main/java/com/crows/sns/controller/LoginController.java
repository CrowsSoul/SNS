package com.crows.sns.controller;

import com.crows.sns.pojo.LoginRequest;
import com.crows.sns.pojo.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//        System.out.println(username);
//        System.out.println(password);
//        if(username.equals("admin") && password.equals("admin"))
//        {
//            return new LoginResponse("Jack",true,"good","student");
//        }
//        else
//        {
//            return new LoginResponse(null,false,"bad",null);
//        }
        return new LoginResponse("Jack",false,"good","student");
    }

}
