/*package com.project.zidio.controller;

import com.project.zidio.entity.*;
import com.project.zidio.repository.ApplicationRepository;
import com.project.zidio.repository.JobRepository;
import com.project.zidio.repository.StudentRepository;
import com.project.zidio.repository.UserRepository;
import com.project.zidio.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final SubscriptionPlanRepository SubscriptionPlanRepository;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyToJob(@PathVariable Long jobId,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        User student = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        Job job = jobRepository.findById(jobId).orElseThrow();

        Application application = Application.builder()
                .student(student)
                .job(job)
                .status("APPLIED")
                .appliedAt(LocalDate.now())
                .build();

        applicationRepository.save(application);
        return ResponseEntity.ok("Application submitted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id)
                .map(existing -> {
                    User existingUser = existing.getUser();
                    User updatedUser = updatedStudent.getUser();
                    if (updatedUser != null && existingUser != null) {
                        existingUser.setName(updatedUser.getName());
                        existingUser.setEmail(updatedUser.getEmail());
                    }
                    existing.setUniversity(updatedStudent.getUniversity());
                    existing.setDegree(updatedStudent.getDegree());
                    existing.setYearOfStudy(updatedStudent.getYearOfStudy());
                    existing.setResumeUrl(updatedStudent.getResumeUrl());
                    return ResponseEntity.ok(studentRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }


    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyForJob(@PathVariable Long jobId, Principal principal) {
        User student = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        SubscriptionPlan subscription = SubscriptionPlanRepository.findByUserId(student.getId()).orElse(null);

        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        long appliedCount = applicationRepository.countByStudent_IdAndAppliedAtBetween(
                student.getId(), startOfMonth, endOfMonth);

        // Limit: 5 applications without premium
        if (subscription == null || !"PAID".equals(subscription.getPaymentStatus())) {
            if (appliedCount >= 5) {
                return ResponseEntity.badRequest().body("You reached the free limit of 5 applications this month. Please upgrade.");
            }
        }

        // Save application
        Application app = Application.builder()
                .student(student)
                .job(jobRepository.findById(jobId).orElseThrow())
                .status("APPLIED")
                .appliedAt(LocalDate.now())
                .build();

        applicationRepository.save(app);

        return ResponseEntity.ok("Applied successfully!");
    }

} */
package com.project.zidio.controller;

import com.project.zidio.entity.*;
import com.project.zidio.repository.ApplicationRepository;
import com.project.zidio.repository.JobRepository;
import com.project.zidio.repository.StudentRepository;
import com.project.zidio.repository.UserRepository;
import com.project.zidio.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    // ✅ Apply for job (with subscription enforcement)
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyForJob(@PathVariable Long jobId, Principal principal) {
        User student = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        SubscriptionPlan subscription = subscriptionPlanRepository.findByUserId(student.getId()).orElse(null);

        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        long appliedCount = applicationRepository.countByStudent_IdAndAppliedAtBetween(
                student.getId(), startOfMonth, endOfMonth);

        // Limit: 5 applications without premium
        if (subscription == null || !"PAID".equals(subscription.getPaymentStatus())) {
            if (appliedCount >= 5) {
                return ResponseEntity.badRequest().body("You reached the free limit of 5 applications this month. Please upgrade.");
            }
        }

        Application app = Application.builder()
                .student(student)
                .job(jobRepository.findById(jobId).orElseThrow())
                .status("APPLIED")
                .appliedAt(LocalDate.now())
                .build();

        applicationRepository.save(app);
        return ResponseEntity.ok("Applied successfully!");
    }

    // ✅ Get all students
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    // ✅ Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id)
                .map(existing -> {
                    User existingUser = existing.getUser();
                    User updatedUser = updatedStudent.getUser();
                    if (updatedUser != null && existingUser != null) {
                        existingUser.setName(updatedUser.getName());
                        existingUser.setEmail(updatedUser.getEmail());
                    }
                    existing.setUniversity(updatedStudent.getUniversity());
                    existing.setDegree(updatedStudent.getDegree());
                    existing.setYearOfStudy(updatedStudent.getYearOfStudy());
                    existing.setResumeUrl(updatedStudent.getResumeUrl());
                    return ResponseEntity.ok(studentRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
