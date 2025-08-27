package com.project.zidio.repository;

import com.project.zidio.entity.Job;
import com.project.zidio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByPostedBy(User recruiter);
    long countByPostedBy_IdAndPostedAtBetween(Long recruiterId, LocalDate start, LocalDate end);

    //long countByPostedBy_IdAndCreatedAtBetween(Long postedByUserId, LocalDateTime start, LocalDateTime end);


}
