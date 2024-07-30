package com.crows.sns.controller.activity;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class EditActivityController {
    @Autowired
    private ActivityMapper activityMapper;

    @PutMapping("/{id}")
    public void editActivity(@PathVariable int id,@RequestBody Activity request)
    {
        request.setActivityTime();
        activityMapper.updateActivity(request);
    }
}
