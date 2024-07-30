package com.crows.sns.controller.resume;

import com.crows.sns.pojo.Resume;
import com.crows.sns.service.Impl.ResumeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class GetAllResumesController {
    @Autowired
    private ResumeServiceImpl resumeServiceImpl;

    @GetMapping
    public List<Resume> getAllResumes()
    {
        return resumeServiceImpl.getAllResumes();
    }

}
