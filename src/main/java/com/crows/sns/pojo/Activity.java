package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @JsonProperty("id")
    private int activity_id;
    @JsonProperty("name")
    private String activity_name;
    @JsonProperty("description")
    private String activity_description;
    @JsonProperty("location")
    private String activity_location;
    @JsonProperty("time")
    private LocalDateTime activity_time;
    private String initiator;//nickname
    private int maxParticipants;
    private int currentParticipants;
    private String status;
    private List<String> participantList;
}
