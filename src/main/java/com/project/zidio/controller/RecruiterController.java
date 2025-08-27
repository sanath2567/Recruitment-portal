package com.project.zidio.controller;

import com.project.zidio.entity.Application;
import com.project.zidio.entity.Job;
import com.project.zidio.entity.SubscriptionPlan;
import com.project.zidio.entity.User;
import com.project.zidio.repository.ApplicationRepository;
import com.project.zidio.repository.JobRepository;
import com.project.zidio.repository.UserRepository;
import com.project.zidio.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository SubscriptionPlanRepository;

    @GetMapping("/applications/{jobId}")
    public ResponseEntity<List<Application>> getApplications(@PathVariable Long jobId,
                                                             @AuthenticationPrincipal UserDetails userDetails) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        List<Application> applications = applicationRepository.findByJob(job);
        return ResponseEntity.ok(applications);
    }


    @PostMapping("/jobs")
    public ResponseEntity<String> postJob(@RequestBody Job job, Principal principal) {
        User recruiter = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        SubscriptionPlan subscription = SubscriptionPlanRepository.findByUserId(recruiter.getId()).orElse(null);

        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        long postedCount = jobRepository.countByPostedBy_IdAndPostedAtBetween(
                recruiter.getId(), startOfMonth, endOfMonth);

        // Limit: 10 jobs per month without premium
        if (subscription == null || !"PAID".equals(subscription.getPaymentStatus())) {
            if (postedCount >= 10) {
                return ResponseEntity.badRequest().body("You reached the free limit of 10 job posts this month. Please upgrade.");
            }
        }

        job.setPostedBy(recruiter);
        job.setPostedAt(LocalDate.now());
        jobRepository.save(job);

        return ResponseEntity.ok("Job posted successfully!");
    }

}

