package com.resume.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {
    
    @Autowired
    private ResumeRepository resumeRepository;

    public void save(Person person) {
        resumeRepository.save(person);
    }

    public Person getById(Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

}

