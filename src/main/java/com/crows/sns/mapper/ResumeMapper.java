package com.crows.sns.mapper;

import com.crows.sns.pojo.Resume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResumeMapper {

    @Select("SELECT * FROM resumes")
    public List<Resume> getAllResumes();

    @Select("SELECT * FROM user_skill WHERE user_id = #{userId}")
    public List<Integer> getSkillIdsByUserId(int userId);

    @Select("SELECT * FROM resumes WHERE resume_id = #{resumeId}")
    public Resume getResumeById(int resumeId);

    @Update("UPDATE resumes SET content = #{content}, " +
            "status= #{status}, " +
            "work_experience = #{work_experience}," +
            " education = #{education} " +
            "WHERE resume_id = #{resume_id}")
    public void updateResumeById(Resume resume);
}
