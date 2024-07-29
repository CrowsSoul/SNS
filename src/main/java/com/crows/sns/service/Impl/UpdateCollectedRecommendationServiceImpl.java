package com.crows.sns.service.Impl;

import com.crows.sns.mapper.RecommendationMapper;
import com.crows.sns.service.UpdateCollectedRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateCollectedRecommendationServiceImpl implements UpdateCollectedRecommendationService {
    @Autowired
    private RecommendationMapper recommendationMapper;

    @Override
    public void updateCollectedRecommendation(int userId, List<Integer> collected) {
        //先删除掉所有的原有数据
        recommendationMapper.deleteCollectedRecommendationsByUserId(userId);

        //再插入新的收藏数据
        for (int id : collected) {
            recommendationMapper.addUserRecommendation(userId, id);
        }
    }

}
