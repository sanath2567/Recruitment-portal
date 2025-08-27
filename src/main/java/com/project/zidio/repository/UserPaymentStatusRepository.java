package com.project.zidio.repository;

import com.project.zidio.entity.UserPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPaymentStatusRepository extends JpaRepository<UserPaymentStatus, Long> {

    // You can also add custom queries here if needed
    // e.g. List<UserPaymentStatus> findByUserId(Long userId);
    Optional<UserPaymentStatus> findByUserId(Long userId);

}

