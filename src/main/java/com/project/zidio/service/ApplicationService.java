package com.project.zidio.service;

import com.project.zidio.entity.Application;
import com.project.zidio.entity.Job;
import com.project.zidio.entity.User;
import com.project.zidio.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public Application applyToJob(User student, Job job) {
        Application application = Application.builder()
                .student(student)
                .job(job)
                .status("APPLIED")
                .appliedAt(LocalDate.now())
                .build();

        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
