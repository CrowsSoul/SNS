package com.crows.sns.service.Impl;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Comment;
import com.crows.sns.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl implements PostCommentService
{
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Comment addComment(Comment comment) {
        int userId = userMapper.findUserIdByNickname(comment.getAuthor());
        comment.setUserId(userId);
        return comment;
    }
}
