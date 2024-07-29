package com.crows.sns.service.Impl;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Recommendation;
import com.crows.sns.service.PostRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostRecommendationServiceImpl implements PostRecommendationService {
    @Autowired
   private RecommendationMapper recommendationMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Recommendation postRecommendation(Recommendation request) {
        // 获取request，先设置max和min
        request.makeMaxMin();
        // 再获取user_id
        int user_id = userMapper.findUserIdByNickname(request.getAuthor());
        request.setUser_id(user_id);
        //设置状态为false
        request.setStatus(false);

        //存入数据库
        recommendationMapper.addRecommendation(request);
        return request;
    }
}
