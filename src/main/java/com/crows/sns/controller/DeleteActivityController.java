package com.crows.sns.controller;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.pojo.Activity;
import com.crows.sns.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class DeleteActivityController
{
    @Autowired
    private ActivityMapper activityMapper;

    @DeleteMapping("/{id}")
    public Response deleteActivityById(@PathVariable("id") int id)
    {
        Activity activity = activityMapper.getActivityById(id);
        if (activity == null)
        {
            return new Response(false, "Activity not found");
        }
        activityMapper.deleteActivityById(id);
        return new Response(true, "Activity deleted successfully");
    }
}
