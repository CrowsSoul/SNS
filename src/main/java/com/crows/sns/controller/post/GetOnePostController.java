package com.crows.sns.controller.post;

import com.crows.sns.mapper.PostMapper;
import com.crows.sns.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blogs")
public class GetOnePostController {

    @Autowired
    private PostMapper postMapper;

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") int id)
    {
        return postMapper.getPostById(id);
    }
}
