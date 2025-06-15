package com.example.cmr.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.example.cmr.model.Job;
import com.example.cmr.model.JobApplication;
import com.example.cmr.model.User;
import com.example.cmr.repository.JobRepository;
import com.example.cmr.repository.JobApplicationRepository;
import com.example.cmr.repository.UserRepository;


@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    // Apply to a job
    @PostMapping("/{jobId}")
    public ResponseEntity<?> applyToJob(@PathVariable Long jobId, @RequestBody(required = false) Map<String, String> body, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Job job = jobRepository.findById(jobId).orElseThrow();

        // Prevent duplicate applications
        if (applicationRepository.existsByUserAndJob(user, job)) {
            return ResponseEntity.badRequest().body("Already applied to this job.");
        }

        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJob(job);
        if (body != null && body.containsKey("notes")) {
            application.setNotes(body.get("notes"));
        }

        return ResponseEntity.ok(applicationRepository.save(application));
    }

    // Get all applications by logged-in user
    @GetMapping("/my")
    public ResponseEntity<List<JobApplication>> getMyApplications(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        return ResponseEntity.ok(applicationRepository.findByUserId(user.getId()));
    }
    // Update status of a job application (only by the applicant or admin)
    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId,
                                                     @RequestBody Map<String, String> body,
                                                     Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Allow only the user who applied or an admin to update status
        if (!application.getUser().getId().equals(user.getId()) && !user.getRole().equalsIgnoreCase("ADMIN")) {
            return ResponseEntity.status(403).body("You are not authorized to update this application");
        }

        String newStatus = body.get("status");
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Status value is missing");
        }

        application.setStatus(newStatus.trim());
        applicationRepository.save(application);

        return ResponseEntity.ok("Application status updated to: " + newStatus);
    }

}
