package com.crows.sns.controller;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class GetOneCommentController {
    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/{id}")
    public Comment getOneComment(@PathVariable int id)
    {
        return commentMapper.getCommentById(id);
    }
}
