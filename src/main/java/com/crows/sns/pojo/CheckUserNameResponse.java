package com.crows.sns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckUserNameResponse {
    private boolean available;
}
