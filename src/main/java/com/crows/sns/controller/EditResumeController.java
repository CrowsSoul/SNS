package com.crows.sns.controller;

import com.crows.sns.pojo.Resume;
import com.crows.sns.service.Impl.ResumeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes")
public class EditResumeController {
    @Autowired
    private ResumeServiceImpl resumeService;

    @PutMapping("/{id}")
    public Resume editResume(@PathVariable int id, @RequestBody Resume request)
    {
        request.setResume_id(id);
        return resumeService.editResume(request);
    }
}
