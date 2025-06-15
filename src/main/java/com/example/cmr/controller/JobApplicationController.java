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
}
