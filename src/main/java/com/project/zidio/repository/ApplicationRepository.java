package com.project.zidio.repository;

import com.project.zidio.entity.Application;
import com.project.zidio.entity.User;
import com.project.zidio.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(User student);
    List<Application> findByJob(Job job);
    long countByStudent_Id(Long userId);

    long countByStudent_IdAndAppliedAtBetween(Long studentId, LocalDate start, LocalDate end);
}