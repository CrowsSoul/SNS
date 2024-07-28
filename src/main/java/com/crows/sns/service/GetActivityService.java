package com.crows.sns.service;

import com.crows.sns.pojo.Activity;

import java.util.List;

public interface GetActivityService {
    public Activity getOneActivityById(int activityId);
    public List<Activity> getAllActivities();
}
