package com.crows.sns.controller;

import com.crows.sns.pojo.CheckUserNameResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckUserNameController {

    @GetMapping("/check-username")
    public CheckUserNameResponse checkUserName(@RequestParam("username") String username) {
        // 检查用户名逻辑
        return new CheckUserNameResponse(true);
    }
}

