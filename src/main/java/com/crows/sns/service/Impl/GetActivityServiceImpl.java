package com.crows.sns.service.Impl;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.pojo.Activity;
import com.crows.sns.service.GetActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetActivityServiceImpl implements GetActivityService {
    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public Activity getOneActivityById(int activityId) {
        List<Activity> activities = activityMapper.getOneActivitiesWithParticipantsById(activityId);
        Activity activity = activities.getFirst();
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
        // 使用 Map 去重
        Map<Integer, Activity> activityMap = new HashMap<>();
        for (Activity activity : activities) {
            // 如果 Map 中已经包含了这个 activityId，就跳过
            if (!activityMap.containsKey(activity.getActivity_id())) {
                activityMap.put(activity.getActivity_id(), activity);
            }
        }

        // 处理每个活动的参与者列表
        for (Activity activity : activityMap.values()) {
            List<String> participants = activityMapper.selectParticipants(activity.getActivity_id());
            activity.setParticipantList(participants);
        }

        // 返回去重后的活动列表
        return new ArrayList<>(activityMap.values());
    }
}
