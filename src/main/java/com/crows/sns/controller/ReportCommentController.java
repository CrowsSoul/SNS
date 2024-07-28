package com.crows.sns.controller;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportCommentController
{
    @Autowired
    private CommentMapper commentMapper;

    @PutMapping("/comments/{id}/report")
    public Response reportComment(@PathVariable int id)
    {
        commentMapper.reportCommentById(id);
        return new Response(true, "comment reported");
    }
}
