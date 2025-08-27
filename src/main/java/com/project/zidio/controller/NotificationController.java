package com.project.zidio.controller;

import com.project.zidio.entity.Notification;
import com.project.zidio.entity.User;
import com.project.zidio.repository.NotificationRepository;
import com.project.zidio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<Notification>> getMyNotifications(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(notificationRepository.findByRecipient(user));
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam Long userId,
                                                   @RequestParam String message) {
        User user = userRepository.findById(userId).orElseThrow();

        Notification notification = Notification.builder()
                .recipient(user)
                .message(message)
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();

        notificationRepository.save(notification);
        return ResponseEntity.ok("Notification sent");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(
            @PathVariable Long userId,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {

        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Allow access if ADMIN or student accessing their own notifications
        if (!currentUser.getRole().equals("ADMIN") && !currentUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(notificationRepository.findByRecipient_Id(userId));

    }

}

