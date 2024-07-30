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
    public Resume getOneResume(String nickname)
    {
        //获取单个简历
        Resume resume = resumeMapper.getResumeByNickname(nickname);
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
        int resumeId = resume.getResume_id();
        //首先更新resume
        resumeMapper.updateResumeById(resume);
        //再更新skills
        //首先删除原有的所有skills
        int userId = resume.getUser_id();
        resumeMapper.deleteSkillsByUserId(userId);
        //再插入新的skills
        //先获取skills列表
        List<String> skills = resume.getSkills();
        //遍历插入
        for(String skillName:skills)
        {
            resumeMapper.insertSkills(userId, getIdBySkillName(skillName));
        }
        return resumeMapper.getResumeById(resumeId);

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

    private int getIdBySkillName(String skillName) {
        return switch (skillName) {
            case "前端" -> 1;
            case "后端" -> 2;
            case "软件设计" -> 3;
            case "平面设计" -> 4;
            case "网络管理" -> 5;
            case "数据分析" -> 6;
            case "数据库管理" -> 7;
            default -> 0; // 如果ID不匹配任何技能，则返回null
        };
    }
}
