package com.crows.sns.service.Impl;

import com.crows.sns.mapper.ActivityMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Activity;
import com.crows.sns.service.PostNewActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostNewActivityServiceImpl implements PostNewActivityService
{
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Activity postNewActivity(Activity request) {
        //插入activities表中
        //首先需要设置LocalDateTime
        request.setActivityTime();
        //设置status
        request.setStatus("pending");
        activityMapper.insertActivity(request);

        //然后插入关联表
        //先获取User的id
        int userId = userMapper.findUserIdByNickname(request.getInitiator());
        //再获取Activity的id
        //在上面已经自动注入
        int activityId = request.getActivity_id();
        //插入activity_user表中
        activityMapper.insertUserActivity(userId, activityId,"postNewActivity");

        return request;
    }


}
