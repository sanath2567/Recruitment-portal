package com.project.zidio.service;

import  com.project.zidio.dto.JobRequest;
import  com.project.zidio.entity.Job;
import  com.project.zidio.entity.User;
import  com.project.zidio.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job postJob(JobRequest jobRequest, User recruiter) {
        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .type(jobRequest.getType())
                .location(jobRequest.getLocation())
                .remote(jobRequest.isRemote())
                .startDate(jobRequest.getStartDate())
                .durationWeeks(jobRequest.getDurationWeeks())
                .stipend(jobRequest.getStipend())
                .postedAt(LocalDate.now())
                .postedBy(recruiter)
                .build();

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}

