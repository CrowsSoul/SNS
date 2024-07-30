package com.crows.sns.controller.post;

import com.crows.sns.mapper.PostMapper;
import com.crows.sns.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllPostsController {
    @Autowired
    private PostMapper postMapper;

    @GetMapping("/blogs")
    public List<Post> getAllPosts()
    {
        return postMapper.getAllPostsWithAuthorName();
    }
}
