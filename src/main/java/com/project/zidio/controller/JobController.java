/*package com.project.zidio.controller;

import com.project.zidio.dto.JobRequest;
import com.project.zidio.dto.JobResponse;
import com.project.zidio.entity.Job;
import com.project.zidio.entity.User;
import com.project.zidio.repository.JobRepository;
import com.project.zidio.repository.UserRepository;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private java.time.LocalDateTime createdAt;

    @PostMapping
    public ResponseEntity<String> postJob(@RequestBody JobRequest jobRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User recruiter = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

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

        jobRepository.save(job);
        return ResponseEntity.ok("Job posted successfully");
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobRepository.findAll().stream().map(job -> JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .type(job.getType())
                .location(job.getLocation())
                .remote(job.isRemote())
                .startDate(job.getStartDate())
                .durationWeeks(job.getDurationWeeks())
                .stipend(job.getStipend())
                .postedAt(job.getPostedAt())
                .postedByEmail(job.getPostedBy().getEmail())
                .build()).collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }


    @PrePersist
    public void onCreate() {
        if (createdAt == null) createdAt = java.time.LocalDateTime.now();
    }
}

*/

package com.project.zidio.controller;

import com.project.zidio.dto.JobResponse;
import com.project.zidio.entity.Job;
import com.project.zidio.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobRepository jobRepository;

    // ✅ List all jobs
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobRepository.findAll().stream().map(job -> JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .type(job.getType())
                .location(job.getLocation())
                .remote(job.isRemote())
                .startDate(job.getStartDate())
                .durationWeeks(job.getDurationWeeks())
                .stipend(job.getStipend())
                .postedAt(job.getPostedAt())
                .postedByEmail(job.getPostedBy().getEmail())
                .build()).collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }

    // ✅ Get single job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(job -> ResponseEntity.ok(JobResponse.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .type(job.getType())
                        .location(job.getLocation())
                        .remote(job.isRemote())
                        .startDate(job.getStartDate())
                        .durationWeeks(job.getDurationWeeks())
                        .stipend(job.getStipend())
                        .postedAt(job.getPostedAt())
                        .postedByEmail(job.getPostedBy().getEmail())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }
}
