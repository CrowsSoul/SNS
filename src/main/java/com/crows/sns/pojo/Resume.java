package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    private int resume_id;
    private int user_id;
    private String content;
    private String status;
    @JsonProperty("user")
    private String nickname;
    private String work_experience;
    private String education;
    private List<String> skills = new ArrayList<>();
}
