package com.crows.sns.controller;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.pojo.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendations")
public class GetOneRecommendationController {
    @Autowired
    private RecommendationMapper recommendationMapper;

    @GetMapping("/{id}")
    public Recommendation getOneRecommendation(@PathVariable int id)
    {
        Recommendation recommendation = recommendationMapper.getRecommendationById(id);
        recommendation.makeSalaryRange();
        return recommendation;
    }
}
