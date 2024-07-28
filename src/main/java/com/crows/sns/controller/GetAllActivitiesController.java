package com.crows.sns.controller;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllActivitiesController {
    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping("/activities")
    public List<Activity> getAllActivities()
    {
        return activityMapper.getAllActivitiesWithParticipants();
    }
}
