package com.crows.sns.controller.comment;

import com.crows.sns.mapper.CommentMapper;
import com.crows.sns.pojo.Comment;
import com.crows.sns.pojo.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class DeleteCommentController {
    @Autowired
    private CommentMapper commentMapper;

    @DeleteMapping("/{id}")
    public DeleteResponse deleteComment(@PathVariable("id") int id)
    {
        Comment comment = commentMapper.getCommentById(id);
        if(comment == null)
        {
            return new DeleteResponse("Comment not found");
        }
        else
        {
            commentMapper.deleteCommentById(id);
            return new DeleteResponse("Comment deleted");
        }
    }

}
