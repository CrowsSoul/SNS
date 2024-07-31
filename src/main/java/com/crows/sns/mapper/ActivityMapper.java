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
    public List<Activity> getOneActivitiesWithParticipantsById(int id);

    @Insert("INSERT INTO activities(activity_name, initiator, activity_time, activity_location, activity_description, maxParticipants, currentParticipants, status)" +
            "VALUES(#{activity_name}, #{initiator}, #{activity_time}, #{activity_location}, #{activity_description}, #{maxParticipants}, #{currentParticipants}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "activity_id")
    public void insertActivity(Activity activity);

    @Insert("INSERT INTO user_activities(user_id, activity_id,activity_status)" +
            "VALUES(#{userId}, #{activityId}, #{status})")
    public void insertUserActivity(int userId, int activityId,String status);

    @Delete("DELETE FROM activities WHERE activity_id = #{activityId}")
    public void deleteActivityById(int activityId);

    @Select("SELECT * FROM activities WHERE activity_id = #{activityId}")
    public Activity getActivityById(int activityId);

    @Update("UPDATE activities SET activity_name = #{activity_name}, initiator = #{initiator}, activity_time = #{activity_time}, activity_location = #{activity_location}, activity_description = #{activity_description}, maxParticipants = #{maxParticipants}, currentParticipants = #{currentParticipants}, status = #{status} WHERE activity_id = #{activity_id}")
    public void updateActivity(Activity activity);

    @Delete("DELETE FROM user_activities WHERE activity_id = #{activityId}")
    public void deleteUserActivityByActivityId(int activityId);
}
