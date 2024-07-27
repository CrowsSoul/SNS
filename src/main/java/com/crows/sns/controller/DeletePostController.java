package com.crows.sns.controller;

import com.crows.sns.mapper.PostMapper;
import com.crows.sns.pojo.DeletePostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crows.sns.pojo.Post;

@RestController
@RequestMapping("/blogs")
public class DeletePostController {
    @Autowired
    PostMapper postMapper;

    @DeleteMapping("/{id}")
    public DeletePostResponse deletePost(@PathVariable("id") int id)
    {
        Post post = postMapper.getPostById(id);
        if(post!= null)
        {
            postMapper.deletePost(id);
            return new DeletePostResponse("Post deleted successfully");
        }
        else
        {
            return new DeletePostResponse("Post not found");
        }

    }
}
