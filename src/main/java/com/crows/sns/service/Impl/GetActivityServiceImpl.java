package com.crows.sns.service.Impl;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.pojo.Activity;
import com.crows.sns.service.GetActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetActivityServiceImpl implements GetActivityService {
    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public Activity getOneActivityById(int activityId) {
        Activity activity = activityMapper.getOneActivitiesWithParticipantsById(activityId);
        LocalDateTime ldt = activity.getActivity_time();
        // 获取年、月、日、小时和分钟
        Integer year = ldt.getYear();         // 年
        Integer month = ldt.getMonthValue();  // 月（1-12）
        Integer day = ldt.getDayOfMonth();    // 日
        Integer hour = ldt.getHour();         // 小时（0-23）
        Integer minute = ldt.getMinute();     // 分钟（0-59）

        activity.makeTime(year, month, day, hour, minute);
        return activity;
    }

    @Override
    public List<Activity> getAllActivities() {
        List<Activity> activities = activityMapper.getAllActivitiesWithParticipants();
        for (Activity activity : activities) {
            LocalDateTime ldt = activity.getActivity_time();
            // 获取年、月、日、小时和分钟
            Integer year = ldt.getYear();         // 年
            Integer month = ldt.getMonthValue();  // 月（1-12）
            Integer day = ldt.getDayOfMonth();    // 日
            Integer hour = ldt.getHour();         // 小时（0-23）
            Integer minute = ldt.getMinute();     // 分钟（0-59）
            activity.makeTime(year, month, day, hour, minute);
        }
        return activities;
    }
}
