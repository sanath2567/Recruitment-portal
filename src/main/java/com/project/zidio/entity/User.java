package com.project.zidio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String role; // STUDENT, RECRUITER, ADMIN

    private boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;
}

