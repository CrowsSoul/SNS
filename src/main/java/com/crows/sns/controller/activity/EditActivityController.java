package com.crows.sns.controller.activity;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Activity;
import com.crows.sns.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class EditActivityController {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private UserMapper userMapper;

    @PutMapping("/{id}")
    public void editActivity(@PathVariable int id,@RequestBody Activity request)
    {
        request.setCurrentParticipants(request.getParticipantList().size());
        request.setActivityTime();
        activityMapper.updateActivity(request);
        //然后需要更新关联表
        List<String> participants = request.getParticipantList();
        //先删除原有关联
        activityMapper.deleteUserActivityByActivityId(id);
        //将用户名对应到id，进行添加
        for(String participant:participants)
        {
            int participantId = userMapper.findUserIdByNickname(participant);
            activityMapper.insertUserActivity(participantId,id,"updated");
        }
    }
}
