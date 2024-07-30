package com.crows.sns.controller.recommendation;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.pojo.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
public class EditRecommendationController {
    @Autowired
    private RecommendationMapper recommendationMapper;

    @PutMapping("/{id}")
    public Recommendation editRecommendation(@PathVariable("id") int id, @RequestBody Recommendation request)
    {
        //先设置id
        request.setRecommendation_id(id);
        //再设置max和min
        request.makeMaxMin();
        //更新数据库
        recommendationMapper.updateRecommendation(request);
        return request;
    }
}
