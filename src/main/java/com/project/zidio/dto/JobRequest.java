package com.project.zidio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobRequest {
    private String title;
    private String description;
    private String type; // JOB or INTERNSHIP
    private String location;
    private boolean remote;
    private LocalDate startDate;
    private int durationWeeks;
    private double stipend;
}
