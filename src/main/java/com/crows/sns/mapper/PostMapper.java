package com.crows.sns.mapper;

import com.crows.sns.pojo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
//    @Select("SELECT p.post_id,p.status, p.title, p.content, u.username AS author, p.created_time,p.isRecommended " +
//            "FROM posts p " +
//            "JOIN users u ON p.user_id = u.user_id")
    @Select("SELECT * FROM posts ")
    @Results({
            @Result(property = "id", column = "post_id"),
            @Result(property = "publishedAt", column = "created_time")
    })
    public List<Post> getAllPostsWithAuthorName();

//    @Select("SELECT p.post_id,p.status, p.title, p.content, u.username AS author, p.created_time,p.isRecommended " +
//            "FROM posts p " +
//            "JOIN users u ON p.user_id = u.user_id"+
//            " WHERE p.post_id = #{postId}")
    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    @Results({
            @Result(property = "id", column = "post_id"),
            @Result(property = "publishedAt", column = "created_time")
    })
    public Post getPostById(int postId);
}