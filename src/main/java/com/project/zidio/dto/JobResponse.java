package com.project.zidio.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String type;
    private String location;
    private boolean remote;
    private LocalDate startDate;
    private int durationWeeks;
    private double stipend;
    private LocalDate postedAt;
    private String postedByEmail;
}

