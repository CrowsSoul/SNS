package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JsonIgnore
    private LocalDateTime activity_time;
    private List<Integer> time = new ArrayList<>();;
    private String initiator;//nickname
    private int maxParticipants;
    private int currentParticipants;
    private String status;
    private List<String> participantList;

    //设置time
    public void makeTime(Integer year, Integer month, Integer day, Integer hour, Integer minute)
    {
        this.time.add(year);
        this.time.add(month);
        this.time.add(day);
        this.time.add(hour);
        this.time.add(minute);
        this.activity_time = null;
    }

    //设置activity_time
    public void setActivityTime()
    {
        // 获取年、月、日、小时和分钟
        Integer year = time.get(0);          // 年
        Integer month = time.get(1);        // 月（1-12）
        Integer day = time.get(2);          // 日
        Integer hour = time.get(3);         // 小时（0-23）
        Integer minute = time.get(4);      // 分钟（0-59）

        // 构造 LocalDateTime 对象
        this.activity_time = LocalDateTime.of(year, month, day, hour, minute);
    }
}
