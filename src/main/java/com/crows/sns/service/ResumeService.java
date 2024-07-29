package com.crows.sns.service;

import com.crows.sns.pojo.Resume;

import java.util.List;

public interface ResumeService {
    public List<Resume> getAllResumes();
    public Resume getOneResume(int id);
    public Resume editResume(Resume resume);
}
