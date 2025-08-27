package com.project.zidio.service;

import com.project.zidio.entity.Notification;
import com.project.zidio.entity.User;
import com.project.zidio.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByRecipient(user);
    }

    public Notification sendNotification(User recipient, String message) {
        Notification notification = Notification.builder()
                .recipient(recipient)
                .message(message)
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();

        return notificationRepository.save(notification);
    }
}

