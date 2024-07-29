package com.crows.sns.mapper;

import com.crows.sns.pojo.Resume;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResumeMapper {

    @Select("SELECT * FROM resumes")
    public List<Resume> getAllResumes();

    @Select("SELECT skill_id FROM user_skill WHERE user_id = #{userId}")
    public List<Integer> getSkillIdsByUserId(int userId);

    @Select("SELECT * FROM resumes WHERE resume_id = #{resumeId}")
    public Resume getResumeById(int resumeId);

    @Update("UPDATE resumes SET content = #{content}, " +
            "status= #{status}, " +
            "work_experience = #{work_experience}," +
            " education = #{education} " +
            "WHERE resume_id = #{resume_id}")
    public void updateResumeById(Resume resume);

    @Delete("DELETE FROM user_skill WHERE user_id = #{userId}")
    public void deleteSkillsByUserId(int userId);

    @Insert("INSERT INTO user_skill (user_id, skill_id) VALUES (#{userId}, #{skillId})")
    public void insertSkills(int userId, int skillId);

    @Select("SELECT * FROM resumes WHERE nickname = #{nickname}")
    public Resume getResumeByNickname(String nickname);
}
