/*package com.project.zidio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zidio.dto.SubscriptionPlanDTO;
import com.project.zidio.service.SubscriptionPlanService;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionPlanController {

    @Autowired
    private SubscriptionPlanService  subscriptionPlanService;


    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDTO>>getAll(){
        return ResponseEntity.ok(subscriptionPlanService.getAllSubscriptionPlan());
    }

    @PostMapping
    public ResponseEntity<SubscriptionPlanDTO> create(@RequestParam SubscriptionPlanDTO dto) {
        return ResponseEntity.ok(subscriptionPlanService.createSubscription(dto));
    }

}
*/


package com.project.zidio.controller;

import com.project.zidio.entity.SubscriptionPlan;
import com.project.zidio.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanRepository subscriptionRepository;

    // Student/Recruiter sees their own subscription
    @GetMapping("/me")
    public ResponseEntity<?> getMySubscription(Principal principal) {
        // principal.getName() = logged-in user’s email/username
        return subscriptionRepository.findByUserEmail(principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
