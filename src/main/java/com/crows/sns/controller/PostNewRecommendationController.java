package com.crows.sns.controller;

import com.crows.sns.pojo.Recommendation;
import com.crows.sns.service.Impl.PostRecommendationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendations")
public class PostNewRecommendationController
{
    @Autowired
    private PostRecommendationServiceImpl postRecommendationService;

    @PostMapping
    public Recommendation postNewRecommendation(@RequestBody Recommendation request)
    {
        return postRecommendationService.postRecommendation(request);
    }

}
