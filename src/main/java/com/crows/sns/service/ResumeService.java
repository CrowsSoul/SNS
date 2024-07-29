package com.crows.sns.service;

import com.crows.sns.pojo.Resume;

import java.util.List;

public interface ResumeService {
    public List<Resume> getAllResumes();
    public Resume getOneResume(String nickname);
    public Resume editResume(Resume resume);
}
