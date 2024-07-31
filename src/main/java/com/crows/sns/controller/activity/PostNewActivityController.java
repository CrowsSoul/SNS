package com.crows.sns.controller.activity;

import com.crows.sns.pojo.Activity;
import com.crows.sns.service.Impl.PostNewActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class PostNewActivityController {
    @Autowired
    private PostNewActivityServiceImpl postNewActivityService;

    @PostMapping
    public Activity postNewActivity(@RequestBody Activity request)
    {
        request.setCurrentParticipants(1);
        return postNewActivityService.postNewActivity(request);
    }
}
