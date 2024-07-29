package com.crows.sns.mapper;

import com.crows.sns.pojo.Recommendation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommendationMapper {
    @Select("SELECT * FROM recommendation")
    public List<Recommendation> getAllRecommendations();

    @Select("SELECT * FROM recommendation WHERE recommendation_id = #{id}")
    public Recommendation getRecommendationById(int id);

    @Insert("INSERT INTO recommendation(companyName, " +
            "author,content, " +
            "summary, location, status, created_time, " +
            "publish_time, max_salary, min_salary,user_id)" +
            "VALUES (#{companyName}, #{author},#{content}," +
            "#{summary}, #{location}, #{status}, NOW(), " +
            "NOW(), #{max_salary}, #{min_salary}, #{user_id})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "recommendation_id")
    public void addRecommendation(Recommendation recommendation);
}
