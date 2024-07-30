package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isRecommended")
    private boolean isRecommended;
}
