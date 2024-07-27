package com.crows.sns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String role;
    private LocalDateTime created_time;
    private LocalDateTime updated_time;

    public User(String username,String password,String nickname,String role)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
