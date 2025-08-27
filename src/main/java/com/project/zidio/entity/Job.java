package com.project.zidio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User postedBy;

    private String title;
    private String description;
    private String type; // JOB or INTERNSHIP
    private String location;
    private boolean remote;
    private LocalDate startDate;
    private int durationWeeks;
    private double stipend;
    private LocalDate postedAt;
}
