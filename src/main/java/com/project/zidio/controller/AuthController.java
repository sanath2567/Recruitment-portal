package com.project.zidio.controller;

import com.project.zidio.dto.RegistrationRequestDTO;
import com.project.zidio.dto.LoginRequest;
import com.project.zidio.dto.LoginResponse;
import com.project.zidio.entity.Recruiter;
import com.project.zidio.entity.Student;
import com.project.zidio.entity.User;
import com.project.zidio.repository.RecruiterRepository;
import com.project.zidio.repository.StudentRepository;
import com.project.zidio.repository.UserRepository;
import com.project.zidio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final RecruiterRepository recruiterRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDTO dto) {
        System.out.println("Registering user: " + dto.getEmail());
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole().toUpperCase())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        if (dto.getRole().equalsIgnoreCase("STUDENT")) {
            Student student = Student.builder()
                    .user(savedUser)
                    .university(dto.getUniversity())
                    .degree(dto.getDegree())
                    .yearOfStudy(dto.getYearOfStudy())
                    .resumeUrl(dto.getResumeUrl())
                    .build();
            studentRepository.save(student);
        }

        if (dto.getRole().equalsIgnoreCase("RECRUITER")) {
            Recruiter recruiter = Recruiter.builder()
                    .user(savedUser)
                    .companyName(dto.getCompanyName())
                    .companyWebsite(dto.getCompanyWebsite())
                    .build();
            recruiterRepository.save(recruiter);
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponse(token, user.getRole(), "Login successful"));
    }
}
