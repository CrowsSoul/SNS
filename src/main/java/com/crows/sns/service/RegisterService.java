package com.crows.sns.service;

import com.crows.sns.pojo.Response;

public interface RegisterService {
    public Response register(String nickname, String role, String password, String username);
}
