package com.crows.sns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String nickname;
    boolean success;
    String message;
    String role;
}
