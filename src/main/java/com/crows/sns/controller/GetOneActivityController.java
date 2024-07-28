package com.crows.sns.controller;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/activities")
public class GetOneActivityController {
    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping("/{id}")
    public Activity getOneActivity(@PathVariable int id)
    {
        return activityMapper.getOneActivitiesWithParticipantsById(id);
    }
}
