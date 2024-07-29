package com.crows.sns.service;

import java.util.List;

public interface UpdateCollectedRecommendationService {
    public void updateCollectedRecommendation(int userId, List<Integer> collected);
}
