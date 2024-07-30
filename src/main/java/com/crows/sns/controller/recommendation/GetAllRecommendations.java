package com.crows.sns.controller.recommendation;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.pojo.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class GetAllRecommendations {
    @Autowired
    private RecommendationMapper recommendationMapper;

    @GetMapping
    public List<Recommendation> getAllRecommendations()
    {
        List<Recommendation> recommendations = recommendationMapper.getAllRecommendations();
        for(Recommendation recommendation : recommendations)
        {
            recommendation.makeSalaryRange();
        }
        return recommendations;
    }
}
