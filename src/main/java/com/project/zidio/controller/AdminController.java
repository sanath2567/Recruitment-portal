/*package com.project.zidio.controller;

import com.project.zidio.entity.Job;
import com.project.zidio.entity.User;
import com.project.zidio.repository.JobRepository;
import com.project.zidio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}
*/

package com.project.zidio.controller;

import com.project.zidio.entity.Job;
import com.project.zidio.entity.SubscriptionPlan;
import com.project.zidio.entity.User;
import com.project.zidio.repository.JobRepository;
import com.project.zidio.repository.SubscriptionPlanRepository;
import com.project.zidio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final SubscriptionPlanRepository subscriptionRepository;

    // ✅ Existing APIs
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    // ✅ New APIs for Subscription Management

    // Get all subscriptions
    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionRepository.findAll());
    }

    // Get subscription by userId
    @GetMapping("/subscriptions/{userId}")
    public ResponseEntity<SubscriptionPlan> getSubscription(@PathVariable Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create or Update subscription
    @PostMapping("/subscriptions/{userId}")
    public ResponseEntity<SubscriptionPlan> createOrUpdateSubscription(
            @PathVariable Long userId,
            @RequestParam String planType,
            @RequestParam String paymentStatus
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SubscriptionPlan subscription = subscriptionRepository.findByUserId(userId)
                .orElse(SubscriptionPlan.builder()
                        .user(user)
                        .role(user.getRole())
                        .build()
                );

        subscription.setPlanType(planType.toUpperCase());
        subscription.setPaymentStatus(paymentStatus.toUpperCase());
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusMonths(1)); // Example: 1 month validity

        return ResponseEntity.ok(subscriptionRepository.save(subscription));
    }

    // Delete subscription
    @DeleteMapping("/subscriptions/{userId}")
    public ResponseEntity<String> deleteSubscription(@PathVariable Long userId) {
        subscriptionRepository.findByUserId(userId).ifPresent(subscriptionRepository::delete);
        return ResponseEntity.ok("Subscription deleted for user " + userId);
    }
}
