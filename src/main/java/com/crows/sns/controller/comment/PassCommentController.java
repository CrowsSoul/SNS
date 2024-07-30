package com.crows.sns.controller.comment;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class PassCommentController {
    @Autowired
    private CommentMapper commentMapper;

    @PutMapping("/{id}")
    public void passComment(@PathVariable("id") int commentId, @RequestBody Comment comment) {
        commentMapper.passCommentById(commentId);
    }
}
