package com.crows.sns.service;

import com.crows.sns.pojo.LoginResponse;

public interface LoginService {
    public LoginResponse login(String username, String password);
}
