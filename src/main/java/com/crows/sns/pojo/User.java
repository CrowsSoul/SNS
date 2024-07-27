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
    private String password;
    private String nickname;
    private String email;
    private String role;
    private LocalDateTime created_time;
    private LocalDateTime updated_time;
}
