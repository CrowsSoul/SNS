package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private String author;
    private int blogId;
    private String content;
    @JsonProperty("isReported")
    private boolean isReported;
    private int userId;
}
