package com.crows.sns.service;

import com.crows.sns.pojo.RegisterResponse;

public interface RegisterService {
    public RegisterResponse register(String nickname,String role, String password, String username);
}
