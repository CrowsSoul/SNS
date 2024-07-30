package com.crows.sns.controller;

import com.crows.sns.pojo.Resume;
import com.crows.sns.service.Impl.ResumeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resumes")
public class GetOneResumeController
{
    @Autowired
    private ResumeServiceImpl resumeService;

    @GetMapping("/{user}")
    public Resume getOneResume(@PathVariable("user") String user)
    {
        return resumeService.getOneResume(user);
    }
}
