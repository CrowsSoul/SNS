package com.crows.sns.mapper;

import com.crows.sns.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
}
