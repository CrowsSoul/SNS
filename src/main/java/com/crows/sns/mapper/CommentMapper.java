package com.crows.sns.mapper;

import com.crows.sns.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM comments")
    @Results({
            @Result(property = "id", column = "comment_id"),
            @Result(property = "blogId", column = "post_id")
    })
    public List<Comment> getAllComments();

    @Select("SELECT * FROM comments WHERE comment_id = #{id}")
    @Results({
            @Result(property = "id", column = "comment_id"),
            @Result(property = "blogId", column = "post_id")
    })
    public Comment getCommentById(int id);

    @Select("SELECT * FROM comments WHERE post_id = #{id}")
    @Results({
            @Result(property = "id", column = "comment_id"),
            @Result(property = "blogId", column = "post_id")
    })
    public List<Comment> getCommentsByPostId(int id);

    @Insert("INSERT INTO comments (post_id, user_id, author, content, status, isReported, created_time, updated_time) " +
        "VALUES (#{blogId}, #{userId}, #{author}, #{content}, 'approved', #{isReported}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int addComment(Comment comment);

    @Delete("DELETE FROM comments WHERE comment_id = #{id}")
    public void deleteCommentById(int id);

    @Update("UPDATE comments SET isReported = true WHERE comment_id = #{id}")
    public void reportCommentById(int id);

    @Update("UPDATE comments SET status = 'approved',isReported = false WHERE comment_id = #{id}")
    public void passCommentById(int id);

}
