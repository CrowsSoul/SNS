package com.crows.sns.controller.recommendation;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendations")
public class DeleteRecommendationController {
    @Autowired
    private RecommendationMapper recommendationMapper;

    @DeleteMapping("/{id}")
    public Response deleteRecommendation(@PathVariable int id) {
        recommendationMapper.deleteRecommendationById(id);
        return new Response(true, "success");
    }
}
