package com.crows.sns.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishNewPostRequest {
    private String title;
    private String content;
    private String author;
    private String status;
    private boolean isRecommended;
}
