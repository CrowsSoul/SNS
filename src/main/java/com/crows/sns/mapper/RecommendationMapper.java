package com.crows.sns.mapper;

import com.crows.sns.pojo.Recommendation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommendationMapper {
    @Select("SELECT * FROM recommendation")
    public List<Recommendation> getAllRecommendations();
}
