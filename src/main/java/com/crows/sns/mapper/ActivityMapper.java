package com.crows.sns.mapper;

import com.crows.sns.pojo.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {
    @Select("SELECT a.* , ua.activity_id " +
        "FROM activities a " +
        "LEFT JOIN user_activities ua ON a.activity_id = ua.activity_id "
        )
    @Results({
            @Result(property = "participantList", javaType = List.class,
                    column = "activity_id",
                    many = @Many(select = "selectParticipants")),
            @Result(property = "activity_id", column = "activity_id")
    })
    List<Activity> getAllActivitiesWithParticipants();

    @Select("SELECT u.nickname FROM users u " +
            "JOIN user_activities ua ON u.user_id = ua.user_id " +
            "WHERE ua.activity_id = #{activityId}")
    List<String> selectParticipants(int activityId);

    @Select("SELECT a.*, ua.activity_id " +
            "FROM activities a " +
            "LEFT JOIN user_activities ua ON a.activity_id = ua.activity_id " +
            "WHERE a.activity_id = #{id}"
    )
    @Results({
            @Result(property = "participantList", javaType = List.class,
                    column = "activity_id",
                    many = @Many(select = "selectParticipants")),
            @Result(property = "activity_id", column = "activity_id")
    })
    public Activity getOneActivitiesWithParticipantsById(int id);
}
