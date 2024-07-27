package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int id;
    private int userId;
    private String author;
    private String title;
    private String content;
    private String status;
    private LocalDateTime publishedAt;
    @JsonProperty("isRecommended")
    private boolean isRecommended ;
}
