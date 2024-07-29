package com.crows.sns.controller;

import com.crows.sns.mapper.RecommendationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class GetCollectedRecommendationController {
    @Autowired
    private RecommendationMapper recommendationMapper;

    @GetMapping("/{userId}")
    public List<Integer> getCollectedRecommendation(@PathVariable int userId)
    {
        return recommendationMapper.getCollectedRecommendationIdsByUserId(userId);
    }
}
