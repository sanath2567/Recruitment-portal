package com.project.zidio.dto;



import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role;

    // For STUDENT
    private String university;
    private String degree;
    private int yearOfStudy;
    private String resumeUrl;

    // For RECRUITER
    private String companyName;
    private String companyWebsite;
}

