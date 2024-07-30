package com.crows.sns.controller.post;

import com.crows.sns.mapper.PostMapper;
import com.crows.sns.mapper.UserMapper;
import com.crows.sns.pojo.Post;
import com.crows.sns.pojo.PublishNewPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishNewPostController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    @PostMapping("/blogs")
    public Post publishNewPost(@RequestBody PublishNewPostRequest request)
    {
        String author = request.getAuthor();
        String title = request.getTitle();
        String content = request.getContent();

        int userId = userMapper.findUserIdByNickname(author);
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setUserId(userId);
        postMapper.addPost(post);

        return post;
    }

}
