package com.crows.sns.controller.comment;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCommentsController {
    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/comments")
    public List<Comment> getAllComments()
    {
        return commentMapper.getAllComments();
    }
}
