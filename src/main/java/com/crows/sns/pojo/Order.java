package com.crows.sns.pojo;

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
public class Order {
    private int orders_id;
    private int user_id;
    @JsonProperty("user")
    private String nickname;
    private String orders_name;
    private String orders_introduction;
    @JsonProperty("order_price")
    private int orders_price;
    private String orders_status;
    private LocalDateTime created_time;
    private String successful_bidder;
    private List<String> bidders = new ArrayList<>();
}
