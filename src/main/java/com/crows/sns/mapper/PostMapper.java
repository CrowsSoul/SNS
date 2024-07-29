package com.crows.sns.mapper;

import com.crows.sns.pojo.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("SELECT * FROM posts ")
    @Results({
            @Result(property = "id", column = "post_id"),
            @Result(property = "publishedAt", column = "created_time")
    })
    public List<Post> getAllPostsWithAuthorName();

    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    @Results({
            @Result(property = "id", column = "post_id"),
            @Result(property = "publishedAt", column = "created_time")
    })
    public Post getPostById(int postId);

    @Insert("INSERT INTO posts(title, content, user_id, created_time,isRecommended,status,updated_time,author)" +
            " VALUES(#{title}, #{content}, #{userId}, NOW(),0,'pending',NOW(),#{author})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Results({
            @Result(property = "id", column = "post_id"),
            @Result(property = "publishedAt", column = "created_time")
    })
    public void addPost(Post post);

    @Update("UPDATE posts SET title = #{title}, " +
            "content = #{content}, status = #{status}, updated_time = NOW(), " +
            "author = #{author},isRecommended =#{isRecommended} WHERE post_id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Results({
            @Result(property = "id", column = "post_id"),
            @Result(property = "publishedAt", column = "created_time")
    })
    public void updatePost(Post post);

    @Delete("DELETE FROM posts WHERE post_id = #{id}")
    public void deletePost(int id);


}