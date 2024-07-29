package com.crows.sns.service.Impl;

import com.crows.sns.mapper.ResumeMapper;
import com.crows.sns.pojo.Resume;
import com.crows.sns.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService
{
    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public List<Resume> getAllResumes() {
        //先获取所有简历
        List<Resume> resumes = resumeMapper.getAllResumes();
        //再对每个简历的skills进行填充
        for(Resume resume:resumes)
        {
            int userId = resume.getUser_id();
            //根据id获取用户的skills
            List<Integer> skillIds = resumeMapper.getSkillIdsByUserId(userId);
            List<String> skills = new ArrayList<>();
            //根据skillId获取skill名称并添加进skills列表
            for(int skillId:skillIds)
            {
                String skillName = getSkillNameById(skillId);
                if (skillName != null) {
                    skills.add(skillName);
                }
            }
            resume.setSkills(skills);
        }
        return resumes;
    }

    @Override
    public Resume getOneResume(int id)
    {
        //获取单个简历
        Resume resume = resumeMapper.getResumeById(id);
        //填充skills
        int userId = resume.getUser_id();
        //根据id获取用户的skills
        List<Integer> skillIds = resumeMapper.getSkillIdsByUserId(userId);
        List<String> skills = new ArrayList<>();
        //根据skillId获取skill名称并添加进skills列表
        for(int skillId:skillIds)
        {
            String skillName = getSkillNameById(skillId);
            if (skillName != null) {
                skills.add(skillName);
            }
        }
        resume.setSkills(skills);
        return resume;
    }

    @Override
    public Resume editResume(Resume resume)
    {
        //首先更新resume
        return null;
    }

    private String getSkillNameById(int skillId) {
        return switch (skillId) {
            case 1 -> "前端";
            case 2 -> "后端";
            case 3 -> "软件设计";
            case 4 -> "平面设计";
            case 5 -> "网络管理";
            case 6 -> "数据分析";
            case 7 -> "数据库管理";
            default -> null; // 如果ID不匹配任何技能，则返回null
        };
    }
}
