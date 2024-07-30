package com.crows.sns.controller.comment;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCommentsOfOnePostController
{
    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/blogs/{id}/comments")
    public List<Comment> getCommentsOfOnePost(@PathVariable int id)
    {
        return commentMapper.getCommentsByPostId(id);
    }
}
