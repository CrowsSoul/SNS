package com.crows.sns.controller.activity;

import com.crows.sns.pojo.Activity;
import com.crows.sns.service.Impl.GetActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/activities")
public class GetOneActivityController {
    @Autowired
    private GetActivityServiceImpl getActivityService;

    @GetMapping("/{id}")
    public Activity getOneActivity(@PathVariable int id)
    {
        return getActivityService.getOneActivityById(id);
    }
}
