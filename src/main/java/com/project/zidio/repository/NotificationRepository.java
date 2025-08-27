package com.project.zidio.repository;


import com.project.zidio.entity.Notification;
import com.project.zidio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(User recipient);
   // List<Notification> findByUserId(Long userId);

    List<Notification> findByRecipient_Id(Long userId);
}
