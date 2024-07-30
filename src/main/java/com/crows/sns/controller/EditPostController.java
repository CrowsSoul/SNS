package com.crows.sns.controller;

import com.crows.sns.mapper.PostMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Post;
import com.crows.sns.pojo.PublishNewPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
public class EditPostController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    @PutMapping("/{id}")
    public Post editPost(@PathVariable int id, @RequestBody PublishNewPostRequest request)
    {
        String author = request.getAuthor();
        String title = request.getTitle();
        String content = request.getContent();
        String status = request.getStatus();
        boolean isRecommended = request.isRecommended();

        int userId = userMapper.findUserIdByNickname(author);
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(userId);
        post.setStatus(status);
        post.setId(id);
        post.setRecommended(isRecommended);

        postMapper.updatePost(post);
        return post;
    }
}
