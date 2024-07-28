package com.crows.sns.controller;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Comment;
import com.crows.sns.service.Impl.PostCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostCommentController {
    @Autowired
    private PostCommentServiceImpl postCommentService;

    @PostMapping("/comments")
    public Comment postComment(@RequestBody Comment comment)
    {
         return postCommentService.addComment(comment);
    }
}
