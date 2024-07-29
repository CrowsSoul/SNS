package com.crows.sns.mapper;

import com.crows.sns.pojo.Recommendation;
import org.apache.ibatis.annotations.*;

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

    @Update("UPDATE recommendation SET companyName = #{companyName}, " +
            "author = #{author}, content = #{content}, " +
            "summary = #{summary}, location = #{location}, status = #{status}, " +
            "max_salary = #{max_salary}, min_salary = #{min_salary},publish_time = NOW() " +
            "WHERE recommendation_id = #{recommendation_id}")
    public void updateRecommendation(Recommendation recommendation);

}
