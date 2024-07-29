package com.crows.sns.controller;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.pojo.Response;
import com.crows.sns.service.Impl.UpdateCollectedRecommendationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class UpdateCollectedRecommendationController {
    @Autowired
    private UpdateCollectedRecommendationServiceImpl updateCollectedRecommendationService;

    @PutMapping("/{id}")
    public Response updateRecommendation(@PathVariable int id, @RequestBody List<Integer> collected)
    {
        updateCollectedRecommendationService.updateCollectedRecommendation(id, collected);
        return new Response(true, "success");
    }

}
